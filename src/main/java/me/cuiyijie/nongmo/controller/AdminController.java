package me.cuiyijie.nongmo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.service.CategoryService;
import me.cuiyijie.nongmo.trans.TransAlbumRequest;
import me.cuiyijie.nongmo.trans.TransBasePageRequest;
import me.cuiyijie.nongmo.trans.TransCategoryRequest;
import me.cuiyijie.nongmo.transfer.response.TransBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2021/12/27 11:16
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "listCategory", method = RequestMethod.POST)
    private TransBaseResponse listAllCategory(@RequestBody TransCategoryRequest transCategoryRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        IPage<Category> categoryPage = categoryService.pageFind(transCategoryRequest.getCurrent(),
                transCategoryRequest.getPageSize(),
                transCategoryRequest.getQuery());

        transBaseResponse.setObj(categoryPage);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }


    @RequestMapping(value = "listAlbum", method = RequestMethod.POST)
    private TransBaseResponse listAlbum(@RequestBody TransAlbumRequest transAlbumRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();
        return transBaseResponse;
    }

}
