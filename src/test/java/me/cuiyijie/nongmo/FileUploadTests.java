package me.cuiyijie.nongmo;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.nongmo.entity.FileUpload;
import me.cuiyijie.nongmo.service.FileUploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@SpringBootTest
public class FileUploadTests {

    @Autowired
    private FileUploadService fileUploadService;

    @Test
    void contextLoads() throws IOException {

//        File file = new File("/Users/cuiyijie/Downloads/2953557786920565131.MP4");
//        FileInputStream inputStream = new FileInputStream(file);
//        FileUpload fileUpload = fileUploadService.uploadFile("", file.getName(), inputStream);
//        log.info("upload file success: {}", fileUpload);
    }

}
