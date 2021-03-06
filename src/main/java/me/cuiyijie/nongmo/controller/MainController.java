package me.cuiyijie.nongmo.controller;

import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.service.AlbumService;
import me.cuiyijie.nongmo.service.CategoryService;
import me.cuiyijie.nongmo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 18:37
 */
@Controller
public class MainController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    AlbumService albumService;

    @RequestMapping(value = {"/page/{page}", "/"})
    public String index(Model model, @PathVariable(value = "page") Optional<Integer> maybePage) {
        Pageable pageable = PageRequest.of(maybePage.map(integer -> integer - 1).orElse(0), 20,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        PageUtil.PageResp<Album> albumPageResp = albumService.pageFind(pageable);
        model.addAttribute("albumPage", albumPageResp);
        List<Album> latestAlbum = albumService.getLatestPopularAlbum();
        model.addAttribute("latestAlbum", latestAlbum);
        return "index";
    }

    @RequestMapping(value = {"/category/{category}/page/{page}/", "/category/{category}/"})
    public String showCategory(Model model,
                               @PathVariable(value = "category") String categoryName,
                               @PathVariable(value = "page") Optional<Integer> maybePage) {
        Pageable pageable = PageRequest.of(maybePage.map(integer -> integer - 1).orElse(0), 20,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        PageUtil.PageResp<Album> albumPageResp =
                albumService.pageFindByCategory(categoryName, pageable);
        model.addAttribute("albumPage", albumPageResp);
        List<Album> latestAlbum = albumService.getLatestPopularAlbum();
        model.addAttribute("latestAlbum", latestAlbum);
        model.addAttribute("category", categoryName);
        return "index";
    }

    //    @RequestMapping(value = {"/post/{post}/**"})
    //    public String post(Model model,
    //                       @PathVariable(value = "post") String postName,
    //                       HttpServletRequest request) throws UnsupportedEncodingException {
    //        String realName = URLDecoder.decode(postName + "/" + extractPathFromPattern(request), "utf-8");
    @RequestMapping(value = {"/post/{post}/"})
    public String post(Model model, @PathVariable(value = "post") String postName) throws UnsupportedEncodingException {
        String realName = URLDecoder.decode(postName, "utf-8");
        Optional<Album> maybeAlbum = albumService.findByName(realName);
        if (maybeAlbum.isPresent()) {
            Album album = maybeAlbum.get();
            List<Picture> pictures = albumService.findAllPicture(album.getId());
            model.addAttribute("album", album);
            model.addAttribute("pictures", pictures);
            List<Album> latestAlbum = albumService.getLatestPopularAlbum();
            model.addAttribute("latestAlbum", latestAlbum);
        } else {

        }
        return "post";
    }

    @RequestMapping(value = {"/post_id/{albumId}"})
    public String newPost(Model model, @PathVariable(value = "albumId") Long postId) throws UnsupportedEncodingException {
        Optional<Album> maybeAlbum = albumService.findById(postId);
        if (maybeAlbum.isPresent()) {
            Album album = maybeAlbum.get();
            List<Picture> pictures = albumService.findAllPicture(album.getId());
            model.addAttribute("album", album);
            model.addAttribute("pictures", pictures);
            List<Album> latestAlbum = albumService.getLatestPopularAlbum();
            model.addAttribute("latestAlbum", latestAlbum);
        } else {

        }
        return "post";
    }

    private String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }


    @RequestMapping(value = {"search"})
    public String search(Model model, @RequestParam String key) {

        List<Album> resultAlbum = albumService.findAlbumByTitleBy(key);
        model.addAttribute("key", key);
        model.addAttribute("searchResult", resultAlbum);

        return "search";
    }

    @ResponseBody
    @RequestMapping(value = {"view"})
    public String addView(@RequestParam Long id) {
        albumService.addView(id);
        return "ok";
    }
}
