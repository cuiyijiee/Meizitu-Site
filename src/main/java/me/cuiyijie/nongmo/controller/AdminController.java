package me.cuiyijie.nongmo.controller;

import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.vo.AlbumDetailVO;
import me.cuiyijie.nongmo.entity.vo.AlbumVO;
import me.cuiyijie.nongmo.entity.vo.CategoryVO;
import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.service.CategoryService;
import me.cuiyijie.nongmo.trans.request.TransAlbumRequest;
import me.cuiyijie.nongmo.trans.request.TransCategoryRequest;
import me.cuiyijie.nongmo.trans.response.TransBaseResponse;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "category/list", method = RequestMethod.POST)
    private TransBaseResponse listAllCategory(@RequestBody TransCategoryRequest transCategoryRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        PageUtil.PageResp<CategoryVO> categoryPage = categoryService.pageFind(transCategoryRequest.getCurrent(),
                transCategoryRequest.getPageSize(),
                transCategoryRequest.getQuery());

        transBaseResponse.setObj(categoryPage);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }


    @RequestMapping(value = "album/list", method = RequestMethod.POST)
    private TransBaseResponse listAlbum(@RequestBody TransAlbumRequest transAlbumRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        PageUtil.PageResp<AlbumVO> albumPageResp = albumService.listAlbum(transAlbumRequest);
        transBaseResponse.setObj(albumPageResp);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }

    @RequestMapping(value = "album/detail", method = RequestMethod.POST)
    private TransBaseResponse getAlbumDetail(@RequestBody TransAlbumRequest transAlbumRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        AlbumDetailVO albumDetailVO = albumService.getAlbumDetail(transAlbumRequest.getId());
        transBaseResponse.setObj(albumDetailVO);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }

}
