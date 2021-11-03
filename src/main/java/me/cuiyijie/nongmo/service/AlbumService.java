package me.cuiyijie.nongmo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.dao.PictureDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public PageUtil.PageResp<Album> pageFind(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "created_at DESC");
        Page<Album> result = albumDao.findAll(new Album());
        PageHelper.clearPage();
        return PageUtil.convertFromPage(result);
    }

    public PageUtil.PageResp<Album> pageFindByCategory(Integer pageNum,Integer pageSize,String categoryName) {
        Category category = categoryService.findByName(categoryName);
        PageHelper.startPage(pageNum,pageSize,"created_at DESC");
        Album album = new Album();
        album.setCategory(category == null ? null : category.getId());
        Page<Album> albums = albumDao.findAll(album);
        PageHelper.clearPage();
        return PageUtil.convertFromPage(albums);
    }

    public Optional<Album> findByName(String name) {
        Album album = new Album();
        album.setTitle(name);
        return albumDao.findAll(album).stream().findFirst();
    }

    public Optional<Album> findById(long albumId) {
        Album album = new Album();
        album.setId(albumId);
        return albumDao.findAll(album).stream().findFirst();
    }

    public List<Picture> findAllPicture(long albumId) {
        Picture picture = new Picture();
        picture.setAlbumId(albumId);
        List<Picture> pictures = pictureDao.findAll(picture);
        pictures.sort(Comparator.comparingInt(Picture::getIndex));
        return pictures;
    }

    public List<Album> getLatestPopularAlbum() {
        long nowTimestamp = System.currentTimeMillis();
        if (nowTimestamp - lastObtainLatestTimestamp > 60 * 1000 || latestPopularTenAlbum.size() == 0) {
            lastObtainLatestTimestamp = nowTimestamp;
            PageHelper.startPage(1,100,"view_num DESC");
            latestPopularTenAlbum = albumDao.findAll(new Album());
            PageHelper.clearPage();
        }
        List<Album> result = new ArrayList<>();
        if(latestPopularTenAlbum.size() > 0){
            for (int index = 0; index < 10; index++) {
                int randomAlbumIndex = (int) (Math.random() * latestPopularTenAlbum.size());
                result.add(latestPopularTenAlbum.get(randomAlbumIndex));
            }
        }
        result.sort((o1, o2) -> o2.getViewNum() - o1.getViewNum());
        return result;
    }

    public List<Album> findAlbumByTitleBy(String title) {
        return albumDao.findByTitleLike(title);
    }

    public int addView(Long id) {
        return albumDao.addViewNum(id);
    }
}
