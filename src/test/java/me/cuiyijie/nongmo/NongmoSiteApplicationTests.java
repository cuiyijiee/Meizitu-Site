package me.cuiyijie.nongmo;

import me.cuiyijie.nongmo.mapper.PictureMapper;
import me.cuiyijie.nongmo.service.AlbumAutoCheckService;
import me.cuiyijie.nongmo.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NongmoSiteApplicationTests {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private AlbumAutoCheckService albumAutoCheck;

    @Test
    void contextLoads() {
        albumAutoCheck.check();
    }
}
