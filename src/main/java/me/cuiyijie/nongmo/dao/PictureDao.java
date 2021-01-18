package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/17 12:53
 */
public interface PictureDao extends JpaRepository<Picture, Long>, JpaSpecificationExecutor<Picture> {

    @Query("select p from Picture p where p.albumId = ?1")
    List<Picture> findByAlbumId(long albumId);
}
