package me.cuiyijie.nongmo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("me.cuiyijie.nongmo.dao")
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class NongmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NongmoApplication.class, args);
    }
}
