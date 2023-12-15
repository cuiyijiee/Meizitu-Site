package me.cuiyijie.nongmo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description minio配置类
 * @Author cuiyuijie
 * @Version 1.0
 * @Date 2023/6/12 11:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private final static String HTTP = "http://";

    //endPoint是一个URL，域名，IPv4或者IPv6地址
    private String endpoint;

    //TCP/IP端口号
    private int port;

    //accessKey类似于用户ID，用于唯一标识你的账户
    private String accessKey;

    //secretKey是你账户的密码
    private String secretKey;

    //如果是true，则用的是https而不是http,默认值是true
    private Boolean secure;

    //默认存储桶
    private String bucketName;

    //预览地址
    private String previewUrl;

}
