package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/17 12:53
 */
@Repository
public interface PictureDao {

    List<Picture> findAll(@Param("item") Picture picture);

}
