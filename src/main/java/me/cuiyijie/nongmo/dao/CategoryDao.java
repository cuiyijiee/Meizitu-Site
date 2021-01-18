package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:51
 */

@Repository
public interface CategoryDao extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("select c from Category c where c.name = ?1")
    Category findByCategory(String category);

}
