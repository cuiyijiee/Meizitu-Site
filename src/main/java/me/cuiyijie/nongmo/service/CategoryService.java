package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cuiyijie.nongmo.config.SysConstant;
import me.cuiyijie.nongmo.mapper.AlbumMapper;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.vo.CategoryVO;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import me.cuiyijie.nongmo.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:52
 */

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Autowired
    private AlbumMapper albumMapper;

    public PageUtil.PageResp<CategoryVO> pageFind(Integer current, Integer pageSize, String query) {
        Page<Category> page = new Page<>(current, pageSize);
        baseMapper.selectPage(page, new QueryWrapper<Category>().like("name", query).orderByDesc("show_order"));

        List<CategoryVO> categoryVOS = page.getRecords().stream().map(item -> {
            Long count = albumMapper.selectCount(new QueryWrapper<Album>().eq("category", item.getId()));
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(item, categoryVO);
            categoryVO.setAlbumCount(count);
            return categoryVO;
        }).collect(Collectors.toList());

        return new PageUtil.PageResp<>(page.getTotal(), page.getCurrent(), page.getSize(), categoryVOS);
    }


    @Cacheable(value = SysConstant.CacheKey.CATEGORY)
    public List<Category> findAll() {
        return baseMapper.selectList(new QueryWrapper<Category>().eq("enabled", true).orderByAsc("show_order"));
    }

    public Category findByName(String categoryName) {
        return baseMapper.selectOne(new QueryWrapper<Category>().eq("name", categoryName).eq("enabled", true));
    }

    public Category findById(Long categoryId) {
        return baseMapper.selectById(categoryId);
    }
}
