package me.cuiyijie.nongmo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("me.cuiyijie.nongmo.dao")
@SpringBootApplication
public class NongmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NongmoApplication.class, args);
    }
}
