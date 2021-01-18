package me.cuiyijie.nongmo.service;

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
        return categoryDao.findAll();
    }

    public Category findByName(String categoryName) {
        return categoryDao.findByCategory(categoryName);
    }
}
