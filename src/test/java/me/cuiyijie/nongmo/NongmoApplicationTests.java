package me.cuiyijie.nongmo;

import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class NongmoApplicationTests {

    @Autowired
    private AlbumService albumService;

    @Test
    void contextLoads() {
        Optional<Album> maybeAlbum = albumService.findById(1000);
        if (maybeAlbum.isPresent()) {
            Album album = maybeAlbum.get();
            System.out.println(album.getAlbumUrl());
        } else {
            System.out.println("not found");
        }
    }
}
