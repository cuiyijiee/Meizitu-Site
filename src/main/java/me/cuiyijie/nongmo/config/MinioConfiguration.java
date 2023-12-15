package me.cuiyijie.nongmo.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description MinioConfiguration
 * @Author cuiyuijie
 * @Version 1.0
 * @Date 2023/6/12 15:17
 */
@Configuration
@EnableConfigurationProperties(MinioConfig.class)
public class MinioConfiguration {
    @Resource
    private MinioConfig minioConfig;

    /**
     * 初始化 MinIO 客户端
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint(), minioConfig.getPort(), minioConfig.getSecure())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

}
