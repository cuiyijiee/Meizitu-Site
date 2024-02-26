package me.cuiyijie.nongmo.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cuiyijie.nongmo.config.SysConstant;
import me.cuiyijie.nongmo.mapper.AlbumMapper;
import me.cuiyijie.nongmo.mapper.PictureMapper;
import me.cuiyijie.nongmo.mapper.TagMapper;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.entity.Tag;
import me.cuiyijie.nongmo.entity.vo.AlbumDetailVO;
import me.cuiyijie.nongmo.entity.vo.AlbumVO;
import me.cuiyijie.nongmo.trans.request.TransAlbumRequest;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:39
 */
@Service
public class AlbumService extends ServiceImpl<AlbumMapper, Album> {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private SearchRecordService searchRecordService;

    public PageUtil.PageResp<AlbumVO> listAlbum(TransAlbumRequest transAlbumRequest) {
        Page<Album> page = new Page<>(transAlbumRequest.getCurrent(), transAlbumRequest.getPageSize());

        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(transAlbumRequest.getQuery())) {
            queryWrapper.like("title", transAlbumRequest.getQuery());
        }

        if (transAlbumRequest.getCategory() != null) {
            queryWrapper.eq("category", transAlbumRequest.getCategory());
        }

        if (transAlbumRequest.getOrderBy() != null) {
            switch (transAlbumRequest.getOrderBy()) {
                case viewNumAsc:
                    queryWrapper.orderByAsc("view_num");
                    break;
                case viewNumDesc:
                    queryWrapper.orderByDesc("view_num");
                    break;
                case createdAtAsc:
                    queryWrapper.orderByAsc("created_at");
                    break;
                case createdAtDesc:
                    queryWrapper.orderByDesc("created_at");
                    break;
            }
        }
        baseMapper.selectPage(page, queryWrapper);

        List<AlbumVO> albumVOS = page.getRecords().stream().map(item -> {
            AlbumVO albumVO = new AlbumVO();
            Long count = pictureMapper.selectCount(new QueryWrapper<Picture>().eq("album_id", item.getId()));
            BeanUtils.copyProperties(item, albumVO);
            albumVO.setPictureCount(count);
            return albumVO;
        }).collect(Collectors.toList());

        return new PageUtil.PageResp<>(page.getTotal(), page.getCurrent(), page.getSize(), albumVOS);
    }

    public AlbumDetailVO getAlbumDetail(long albumId) {

        AlbumDetailVO albumDetailVO = new AlbumDetailVO();
        Album album = baseMapper.selectById(albumId);
        albumDetailVO.setAlbum(album);

        if (null != album) {
            Category category = categoryService.findById(album.getCategory());
            albumDetailVO.setCategory(category);
        }

        List<Picture> pictureList = pictureMapper.selectList(new QueryWrapper<Picture>().eq("album_id", albumId));
        albumDetailVO.setPictureList(pictureList);

        List<Tag> tagList = tagMapper.selectAlbumTags(albumId);
        albumDetailVO.setTagList(tagList);

        return albumDetailVO;
    }


    public PageUtil.PageResp<Album> pageFind(Integer pageNum, Integer pageSize) {
        Page<Album> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("created_at"));
        baseMapper.selectPage(page, new QueryWrapper<>());
        return PageUtil.convertFromPage(page);
    }

    public PageUtil.PageResp<Album> pageFindByCategory(Integer pageNum, Integer pageSize, String categoryName) {
        Optional<Category> categoryOptional = categoryService.findAll()
                .stream()
                .filter(category ->category != null && category.getEnabled() && category.getName().equalsIgnoreCase(categoryName))
                .findFirst();
        if(categoryOptional.isPresent()) {
            Page<Album> page = new Page<>(pageNum, pageSize);
            page.addOrder(OrderItem.desc("created_at"));
            baseMapper.selectPage(page, new QueryWrapper<Album>().eq("category", categoryOptional.get().getId()));
            return PageUtil.convertFromPage(page);
        }else {
            return PageUtil.defaultNull();
        }
    }

    public PageUtil.PageResp<Album> pageFindByTag(Integer pageNum, Integer pageSize, String tagName) {
        Tag tag = tagMapper.selectByName(tagName);
        Page<Album> page = new Page<>(pageNum, pageSize);
        baseMapper.pageFindByTag(page, tag.getId());
        return PageUtil.convertFromPage(page);
    }

    public Album findByName(String name) {
        Album album = new Album();
        album.setTitle(name);
        return baseMapper.selectOne(new QueryWrapper<Album>().eq("title", name));
    }

    public Album findById(long albumId) {
        return baseMapper.selectById(albumId);
    }

    @Cacheable(value = SysConstant.CacheKey.ALBUM_PICTURE)
    public List<Picture> findAllPicture(long albumId) {
        return pictureMapper.selectList(new QueryWrapper<Picture>().eq("album_id", albumId));
    }

    @Cacheable(value = SysConstant.CacheKey.RANDOM_ALBUM)
    public List<Album> getRandomAlbum() {
        List<Album> result = baseMapper.findByRandom(10);
        result.sort((o1, o2) -> o2.getViewNum() - o1.getViewNum());
        return result;
    }

    @Cacheable(value = SysConstant.CacheKey.POPULAR_ALBUM)
    public List<Album> getLatestPopularAlbum() {
        Page<Album> page = new Page<>(1, 10);
        List<Album> albumList = baseMapper.selectPage(page, new QueryWrapper<Album>().orderByDesc("view_num")).getRecords();
        return albumList;
    }

    public List<Album> findAlbumByTitle(String title, String ip) {
        searchRecordService.insertNewSearchRecord(title, ip);
        return baseMapper.selectList(new QueryWrapper<Album>().like("title", title).orderByDesc("view_num"));
    }

    public int disableAlbum(long albumId) {
        Album album = new Album();
        album.setId(albumId);
        album.setEnabled(false);
        album.setUpdatedAt(new Date());
        return baseMapper.updateById(album);
    }

    public int addView(Long id) {
        return baseMapper.addViewNum(id);
    }

    public String generateGoogleArticleJson(Album album, List<Picture> pictureList){
        Map<String, Object> metaMap = new HashMap<>();
        metaMap.put("@context","https://schema.org");
        metaMap.put("@type","Article");
        metaMap.put("headline",album.getTitle());
        metaMap.put("image", pictureList.subList(0, Math.min(pictureList.size(), 3)).stream().map(Picture::getUrl).collect(Collectors.toList()));
        metaMap.put("datePublished", DateUtil.format(album.getCreatedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        if(album.getUpdatedAt()!=null){
            metaMap.put("dateModified", DateUtil.format(album.getUpdatedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        Map<String, Object> authorMap = new HashMap<>();
        authorMap.put("@type","Person");
        authorMap.put("name","Nongmo.Zone");
        authorMap.put("url","https://ilovexs.com");
        metaMap.put("author",Arrays.asList(authorMap));
        return JSONUtil.toJsonStr(metaMap);
    }
}
