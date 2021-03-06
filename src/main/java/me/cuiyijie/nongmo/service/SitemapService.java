package me.cuiyijie.nongmo.service;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import me.cuiyijie.nongmo.dao.AlbumDao;
import me.cuiyijie.nongmo.dao.CategoryDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/24 22:17
 */
@Service
public class SitemapService {

    private static final String BASE_URL = "https://www.ilovexs.com";
    private static final String CATEGORY_BASE_URL = "https://www.ilovexs.com/category/%s/";
    private static final String ALBUM_BASE_URL = "https://www.ilovexs.com/post/%s/";
    private static final String NEW_ALBUM_BASE_URL = "https://www.ilovexs.com/post_id/%s/";

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    AlbumDao albumDao;

    public String genSitemapWithIndex(int index, boolean isNew) {
        try {
            WebSitemapGenerator sitemap = new WebSitemapGenerator(BASE_URL);

            WebSitemapUrl indexWebSitemapUrl = new WebSitemapUrl.Options(BASE_URL)
                    .lastMod(toDateString(OffsetDateTime.now()))
                    .changeFreq(ChangeFreq.MONTHLY).priority(1.0).build();
            sitemap.addUrl(indexWebSitemapUrl);
            for (Category category : categoryDao.findAll()) {
                WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(String.format(CATEGORY_BASE_URL, category.getName()))
                        .lastMod(toDateString(category.getCreatedAt()))
                        .changeFreq(ChangeFreq.MONTHLY).priority(1.0).build();
                sitemap.addUrl(webSitemapUrl);
            }
            for (Album album : albumDao.findAll(PageRequest.of(index, 1000))) {
                if (isNew){
                    WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(String.format(NEW_ALBUM_BASE_URL, album.getId()))
                            .lastMod(toDateString(album.getCreatedAt()))
                            .changeFreq(ChangeFreq.MONTHLY).priority(1.0).build();
                    sitemap.addUrl(webSitemapUrl);
                }else{
                    WebSitemapUrl webSitemapUrl = new WebSitemapUrl.Options(String.format(ALBUM_BASE_URL, album.getTitle()))
                            .lastMod(toDateString(album.getCreatedAt()))
                            .changeFreq(ChangeFreq.MONTHLY).priority(1.0).build();
                    sitemap.addUrl(webSitemapUrl);
                }
            }
            return String.join("", sitemap.writeAsStrings());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    private String toDateString(OffsetDateTime dateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(fmt);
    }
}
