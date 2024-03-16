package me.cuiyijie.nongmo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "me.cuiyijie.nongmo.mapper")
@SpringBootApplication
public class NongmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NongmoApplication.class, args);
    }
}
