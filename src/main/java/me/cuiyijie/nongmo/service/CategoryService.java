package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.cuiyijie.nongmo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.cuiyijie.nongmo.dao.CategoryDao;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:52
 */

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<Category> findAll() {
        return categoryDao.selectList(new QueryWrapper<Category>()
                .eq("enabled", true)
                .orderByAsc("show_order"));
    }

    public Category findByName(String categoryName) {
        return categoryDao.selectOne(new QueryWrapper<Category>()
                .eq("name", categoryName)
                .eq("enabled", true));
    }

    public Category findById(Long categoryId) {
        return categoryDao.selectById(categoryId);
    }
}
