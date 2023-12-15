package me.cuiyijie.nongmo.controller.admin;


import me.cuiyijie.nongmo.entity.vo.CategoryVO;
import me.cuiyijie.nongmo.service.CategoryService;
import me.cuiyijie.nongmo.trans.request.TransCategoryRequest;
import me.cuiyijie.nongmo.trans.response.TransBaseResponse;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public TransBaseResponse listAllCategory(@RequestBody TransCategoryRequest transCategoryRequest) {
        TransBaseResponse transBaseResponse = new TransBaseResponse();

        PageUtil.PageResp<CategoryVO> categoryPage = categoryService.pageFind(transCategoryRequest.getCurrent(),
                transCategoryRequest.getPageSize(),
                transCategoryRequest.getQuery());

        transBaseResponse.setObj(categoryPage);
        transBaseResponse.setCode("0");

        return transBaseResponse;
    }

}
