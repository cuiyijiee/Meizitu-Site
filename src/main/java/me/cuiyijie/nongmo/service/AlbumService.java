package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.dao.PictureDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.entity.vo.AlbumDetailVO;
import me.cuiyijie.nongmo.entity.vo.AlbumVO;
import me.cuiyijie.nongmo.trans.request.TransAlbumRequest;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:39
 */
@Service
public class AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PictureDao pictureDao;

    private long lastObtainLatestTimestamp = 0;
    private List<Album> latestPopularTenAlbum = new ArrayList<>();

    public PageUtil.PageResp<AlbumVO> listAlbum(TransAlbumRequest transAlbumRequest) {
        Page<Album> page = new Page<>(transAlbumRequest.getCurrent(), transAlbumRequest.getPageSize());

        QueryWrapper<Album> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", transAlbumRequest.getQuery());

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
        albumDao.selectPage(page, queryWrapper);

        List<AlbumVO> albumVOS = page.getRecords().stream().map(item -> {
            AlbumVO albumVO = new AlbumVO();
            Integer count = pictureDao.selectCount(new QueryWrapper<Picture>()
                    .eq("album_id", item.getId()));
            BeanUtils.copyProperties(item, albumVO);
            albumVO.setPictureCount(count);
            return albumVO;
        }).collect(Collectors.toList());

        return new PageUtil.PageResp<>(page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                albumVOS);
    }

    public AlbumDetailVO getAlbumDetail(Integer id) {

        AlbumDetailVO albumDetailVO = new AlbumDetailVO();
        Album album = albumDao.selectById(id);
        albumDetailVO.setAlbum(album);

        if (null != album) {
            Category category = categoryService.findById(album.getCategory());
            albumDetailVO.setCategory(category);
        }

        List<Picture> pictureList = pictureDao.selectList(new QueryWrapper<Picture>()
                .eq("album_id", id));
        albumDetailVO.setPictureList(pictureList);

        return albumDetailVO;
    }


    public PageUtil.PageResp<Album> pageFind(Integer pageNum, Integer pageSize) {
        Page<Album> page = new Page<>(pageNum, pageSize);
        page.setDesc("created_at");
        albumDao.selectPage(page, new QueryWrapper<>());
        return PageUtil.convertFromPage(page);
    }

    public PageUtil.PageResp<Album> pageFindByCategory(Integer pageNum, Integer pageSize, String categoryName) {
        Category category = categoryService.findByName(categoryName);
        Page<Album> page = new Page<>(pageNum, pageSize);
        page.setDesc("created_at");
        albumDao.selectPage(page, new QueryWrapper<Album>().eq("category", category.getId()));
        return PageUtil.convertFromPage(page);
    }

    public Album findByName(String name) {
        Album album = new Album();
        album.setTitle(name);
        return albumDao.selectOne(new QueryWrapper<Album>().eq("title", name));
    }

    public Album findById(long albumId) {
        return albumDao.selectById(albumId);
    }

    public PageUtil.PageResp<Picture> pageFindPicture(long albumId, Integer pageNum, Integer pageSize) {
        Page<Picture> page = new Page<>(pageNum, pageSize);
        pictureDao.selectPage(page, new QueryWrapper<Picture>()
                .eq("album_id", albumId)
                .orderByAsc("pic_index"));
        return PageUtil.convertFromPage(page);
    }

    public List<Picture> findAllPicture(long albumId) {
        return pictureDao.selectList(new QueryWrapper<Picture>().eq("album_id", albumId));
    }

    public List<Album> getLatestPopularAlbum() {
        long nowTimestamp = System.currentTimeMillis();
        if (nowTimestamp - lastObtainLatestTimestamp > 60 * 1000 || latestPopularTenAlbum.size() == 0) {
            lastObtainLatestTimestamp = nowTimestamp;
            Page<Album> page = new Page<>(1, 100);
            latestPopularTenAlbum = albumDao.selectPage(page, new QueryWrapper<Album>().orderByDesc("view_num")).getRecords();
        }
        List<Album> result = new ArrayList<>();
        if (latestPopularTenAlbum.size() > 0) {
            for (int index = 0; index < 10; index++) {
                int randomAlbumIndex = (int) (Math.random() * latestPopularTenAlbum.size());
                result.add(latestPopularTenAlbum.get(randomAlbumIndex));
            }
        }
        result.sort((o1, o2) -> o2.getViewNum() - o1.getViewNum());
        return result;
    }

    public List<Album> findAlbumByTitleBy(String title) {
        return albumDao.selectList(new QueryWrapper<Album>().like("title", title).orderByDesc("view_num"));
    }

    public int addView(Long id) {
        return albumDao.addViewNum(id);
    }
}
