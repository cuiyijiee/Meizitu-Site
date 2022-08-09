package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.vo.CategoryVO;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.cuiyijie.nongmo.dao.CategoryDao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:52
 */

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    AlbumDao albumDao;

    public PageUtil.PageResp<CategoryVO> pageFind(Integer current, Integer pageSize, String query) {
        Page<Category> page = new Page<>(current, pageSize);
        categoryDao.selectPage(page, new QueryWrapper<Category>()
                .like("name", query)
                .orderByDesc("show_order"));

        List<CategoryVO> categoryVOS = page.getRecords()
                .stream()
                .map(item -> {
                    Integer count = albumDao.selectCount(new QueryWrapper<Album>().eq("category", item.getId()));
                    CategoryVO categoryVO = new CategoryVO();
                    BeanUtils.copyProperties(item, categoryVO);
                    categoryVO.setAlbumCount(count);
                    return categoryVO;
                }).collect(Collectors.toList());

        return new PageUtil.PageResp<CategoryVO>(page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                categoryVOS);
    }


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
