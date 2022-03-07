package me.cuiyijie.nongmo;

import me.cuiyijie.nongmo.dao.PictureDao;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.service.AlbumService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class NongmoSiteApplicationTests {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PictureDao pictureDao;

    @Test
    void contextLoads() {



//        try {
//
//            Document document = Jsoup.connect("https://everia.club/").get();
//            Elements elements = document.getElementsByTag("article");
//
//            for (int index = 0; index < elements.size(); index++) {
//
//                Album album = new Album();
//
//                Element element = elements.get(index);
//                Elements coverElement = element.selectXpath("//img[@class=\"wp-post-image\"]");
//                String coverUrl = coverElement.attr("src");
//                String originId = element.attr("id");
//
//                Elements hrefElement = element.selectXpath("//h2[@class=\"blog-entry-title entry-title\"]/a");
//                String title = hrefElement.text();
//                String href = hrefElement.attr("href");
//
//                album.setOriginId(originId);
//                album.setCoverUrl(coverUrl);
//                album.setTitle(title);
//                album.setAlbumUrl(href);
//
//                List<Picture> pictureList = new ArrayList<>();
//                Document albumDoc = Jsoup.connect(href).get();
//                Elements imageItems = albumDoc.selectXpath("//li[@class=\"blocks-gallery-item\"]/figure/img");
//                for (int imageIndex = 0; imageIndex < imageItems.size(); imageIndex++) {
//                    Element imageEle = imageItems.get(imageIndex);
//                    Picture picture = new Picture();
//                    picture.setPicIndex(imageIndex);
//                    picture.setUrl(imageEle.attr("src"));
//                    pictureList.add(picture);
//                }
//                System.out.println(pictureList);
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
    }
}
