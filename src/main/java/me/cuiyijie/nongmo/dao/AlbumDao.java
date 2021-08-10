package me.cuiyijie.nongmo.dao;

import com.github.pagehelper.Page;
import me.cuiyijie.nongmo.entity.Album;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:38
 */
@Repository
public interface AlbumDao {

    Page<Album> findAll(@Param("item") Album album);

    List<Album> findByTitleLike(@Param("title") String title);

    Integer addViewNum(@Param("id") Long id);
}
