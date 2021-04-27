package me.cuiyijie.nongmo.controller;

import me.cuiyijie.nongmo.service.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/24 22:12
 */
@RestController
public class SitemapController {

    @Autowired
    SitemapService sitemapService;

    @RequestMapping("sitemap{index}.xml")
    public String genSitemap(@PathVariable(required = false) Optional<Integer> index) {
        return sitemapService.genSitemapWithIndex(index.orElse(0), false);
    }

    @RequestMapping("new_sitemap{index}.xml")
    public String genNewSitemap(@PathVariable(required = false) Optional<Integer> index) {
        return sitemapService.genSitemapWithIndex(index.orElse(0), true);
    }

}
