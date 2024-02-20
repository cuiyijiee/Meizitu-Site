package me.cuiyijie.nongmo.controller;

import me.cuiyijie.nongmo.mapper.TagMapper;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.entity.Tag;
import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.service.CategoryService;
import me.cuiyijie.nongmo.util.IpUtil;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 18:37
 */
@Controller
public class MainController {

    @Value("${nongmo.default.pagesize:5}")
    private Integer defaultPageSize;

    @Value("${nongmo.ad-on:false}")
    private boolean isAdOn;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    AlbumService albumService;

    @RequestMapping(value = {"/page/{page}", "/"})
    public String index(Model model, @PathVariable(value = "page") Optional<Integer> maybePage) {
        initBaseModel(model);
        PageUtil.PageResp<Album> albumPageResp =
                albumService.pageFind(maybePage.orElse(1), defaultPageSize);
        model.addAttribute("albumPage", albumPageResp);
        return "index";
    }

    @RequestMapping(value = {"/category/{category_name}/page/{page}/", "/category/{category_name}/"})
    public String showCategory(Model model,
                               @PathVariable(value = "category_name") String categoryName,
                               @PathVariable(value = "page") Optional<Integer> maybePage) {

        initBaseModel(model);

        PageUtil.PageResp<Album> albumPageResp =
                albumService.pageFindByCategory(maybePage.orElse(1), defaultPageSize, categoryName);
        model.addAttribute("albumPage", albumPageResp);
        model.addAttribute("category", categoryName);
        return "index";
    }

    @RequestMapping(value = {"/tag/{tag_name}/page/{page}/", "/tag/{tag_name}/"})
    public String showTag(Model model,
                          @PathVariable(value = "tag_name") String tagName,
                          @PathVariable(value = "page") Optional<Integer> maybePage) {

        initBaseModel(model);

        PageUtil.PageResp<Album> albumPageResp =
                albumService.pageFindByTag(maybePage.orElse(1), defaultPageSize, tagName);
        model.addAttribute("albumPage", albumPageResp);
        model.addAttribute("tag", tagName);
        return "index";
    }

    @RequestMapping(value = {"/post/{post_name}/"})
    public String post(Model model, @PathVariable(value = "post_name") String postName) throws UnsupportedEncodingException {

        initBaseModel(model);

        String realName = URLDecoder.decode(postName, "utf-8");
        Album maybeAlbum = albumService.findByName(realName);
        if (maybeAlbum != null) {
            Category category = categoryService.findById(maybeAlbum.getCategory());
            model.addAttribute("category", category);
            List<Picture> pictureList = albumService.findAllPicture(maybeAlbum.getId());
            List<Tag> tagList = tagMapper.selectAlbumTags(maybeAlbum.getId());
            model.addAttribute("album", maybeAlbum);
            model.addAttribute("pictureList", pictureList);
            model.addAttribute("tagList", tagList);
            model.addAttribute("googleLdJson", albumService.generateGoogleArticleJson(maybeAlbum, pictureList));
        } else {
            return "error";
        }
        return "post";
    }

    @RequestMapping(value = {"/post_id/{post_id}", "/post_id/{post_id}/page/{page_num}"})
    public String newPost(Model model,
                          @PathVariable(value = "post_id") Long postId,
                          @PathVariable(value = "page_num", required = false) Integer pageNum
    ) {

        initBaseModel(model);

        Album maybeAlbum = albumService.findById(postId);
        if (maybeAlbum != null) {
            Category category = categoryService.findById(maybeAlbum.getCategory());
            List<Picture> pictureList = albumService.findAllPicture(maybeAlbum.getId());
            List<Tag> tagList = tagMapper.selectAlbumTags(maybeAlbum.getId());
            model.addAttribute("album", maybeAlbum);
            model.addAttribute("category", category);
            model.addAttribute("pictureList", pictureList);
            model.addAttribute("tagList", tagList);
            model.addAttribute("googleLdJson", albumService.generateGoogleArticleJson(maybeAlbum, pictureList));
        } else {
            return "error";
        }
        return "post";
    }

    @RequestMapping(value = {"search"})
    public String search(Model model, @RequestParam(required = false) String key, HttpServletRequest servletRequest) {

        initBaseModel(model);

        if(StringUtils.hasText(key)){
            List<Album> resultAlbum = albumService.findAlbumByTitle(key, IpUtil.getIpAddr(servletRequest));
            model.addAttribute("searchResult", resultAlbum);
        }else{
            model.addAttribute("searchResult", new ArrayList<>());
        }
        model.addAttribute("key", key);
        return "search";
    }

    @ResponseBody
    @RequestMapping(value = {"view"})
    public String addView(@RequestParam Long id) {
        albumService.addView(id);
        return "ok";
    }

    private void initBaseModel(Model model) {
        model.addAttribute("categorys", categoryService.findAll());
        List<Album> latestAlbum = albumService.getLatestPopularAlbum();
        List<Album> randomAlbum = albumService.getRandomAlbum();
        model.addAttribute("latestAlbum", latestAlbum);
        model.addAttribute("randomAlbum", randomAlbum);
        model.addAttribute("adOn", isAdOn);
    }
}
