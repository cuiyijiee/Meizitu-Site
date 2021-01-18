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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<Album> latestAlbum = albumService.getLatestTenAlbum();
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
        List<Album> latestAlbum = albumService.getLatestTenAlbum();
        model.addAttribute("latestAlbum", latestAlbum);
        model.addAttribute("category", categoryName);
        return "index";
    }

    @RequestMapping(value = {"/post/{post}/"})
    public String post(Model model, @PathVariable(value = "post") String postName) {
        String realName = URLDecoder.decode(postName);
        Optional<Album> maybeAlbum = albumService.findByName(realName);
        if (maybeAlbum.isPresent()) {
            Album album = maybeAlbum.get();
            List<Picture> pictures = albumService.findAllPicture(album.getId());
            model.addAttribute("album", album);
            model.addAttribute("pictures", pictures);
            List<Album> latestAlbum = albumService.getLatestTenAlbum();
            model.addAttribute("latestAlbum", latestAlbum);
        } else {

        }
        return "post";
    }

}
