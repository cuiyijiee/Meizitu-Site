package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.dao.PictureDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public PageUtil.PageResp<Picture> findAllPicture(long albumId, Integer pageNum, Integer pageSize) {
        Page<Picture> page = new Page<>(pageNum, pageSize);
        pictureDao.selectPage(page, new QueryWrapper<Picture>()
                .eq("album_id", albumId)
                .orderByAsc("pic_index"));
        return PageUtil.convertFromPage(page);
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
