package me.cuiyijie.nongmo.service;

import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.dao.CategoryDao;
import me.cuiyijie.nongmo.dao.PictureDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:39
 */
@Service
public class AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    PictureDao pictureDao;

    private long lastObtainLatestTimestamp = 0;
    private List<Album> latestTenAlbum = new ArrayList<>();
    private List<Album> latestPopularTenAlbum = new ArrayList<>();

    public PageUtil.PageResp<Album> pageFind(Pageable pageable) {
        return PageUtil.convertFromPage(albumDao.findAll(pageable));
    }

    public PageUtil.PageResp<Album> pageFindByCategory(String categoryName, Pageable pageable) {
        Category category = categoryDao.findByCategory(categoryName);
        if (category == null) {
            return PageUtil.convertFromPage(albumDao.findAll(pageable));
        } else {
            Specification<Album> specification = new Specification<Album>() {
                @Override
                public Predicate toPredicate(Root<Album> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    //集合 用于封装查询条件
                    List<Predicate> list = new ArrayList<Predicate>();
                    Predicate predicate = criteriaBuilder.equal(root.get("category").as(Category.class), category);
                    list.add(predicate);
                    return criteriaBuilder.and(list.toArray(new Predicate[0]));
                }
            };
            return PageUtil.convertFromPage(albumDao.findAll(specification, pageable));
        }
    }

    public Optional<Album> findByName(String name) {
        Specification<Album> albumSpecification = (root, query, criteriaBuilder) -> {
            //集合 用于封装查询条件
            List<Predicate> list = new ArrayList<Predicate>();
            Predicate predicate = criteriaBuilder.equal(root.get("title").as(String.class), name);
            list.add(predicate);
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        return albumDao.findOne(albumSpecification);
    }

    public Optional<Album> findById(long albumId) {
        Specification<Album> albumSpecification = (root, query, criteriaBuilder) -> {
            //集合 用于封装查询条件
            List<Predicate> list = new ArrayList<>();
            Predicate predicate = criteriaBuilder.equal(root.get("id").as(Long.class), albumId);
            list.add(predicate);
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        return albumDao.findOne(albumSpecification);
    }

    public List<Picture> findAllPicture(long albumId) {
        Specification<Picture> pictureSpecification = new Specification<Picture>() {
            @Override
            public Predicate toPredicate(Root<Picture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //集合 用于封装查询条件
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate predicate = criteriaBuilder.equal(root.get("albumId").as(Long.class), albumId);
                list.add(predicate);
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        List<Picture> pictures = pictureDao.findAll(pictureSpecification);
        pictures.sort(Comparator.comparingInt(Picture::getIndex));
        return pictures;
    }

    public List<Album> getLatestTenAlbum() {
        long nowTimestamp = System.currentTimeMillis();
        if (nowTimestamp - lastObtainLatestTimestamp > 60 * 60 * 1000) {
            lastObtainLatestTimestamp = nowTimestamp;
            latestTenAlbum =
                    albumDao.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"))).toList();
        }
        return latestTenAlbum;
    }

    public List<Album> getLatestPopularAlbum() {
        long nowTimestamp = System.currentTimeMillis();
        if (nowTimestamp - lastObtainLatestTimestamp > 60 * 1000 || latestPopularTenAlbum.size() == 0) {
            lastObtainLatestTimestamp = nowTimestamp;
            latestPopularTenAlbum =
                    albumDao.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "viewNum"))).toList();
        }
        List<Album> result = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            int randomAlbumIndex = (int) (Math.random() * latestPopularTenAlbum.size());
            result.add(latestPopularTenAlbum.get(randomAlbumIndex));
        }
        result.sort((o1, o2) -> o2.getViewNum() - o1.getViewNum());
        return result;
    }

    public List<Album> findAlbumByTitleBy(String title) {
        return albumDao.findByTitleLike(title);
    }

    public int addView(Long id) {
        return albumDao.updateAllView(id);
    }
}
