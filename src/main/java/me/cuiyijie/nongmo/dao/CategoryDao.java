package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:51
 */

@Repository
public interface CategoryDao {

    List<Category> findAll(@Param("item") Category category);

}
