package me.cuiyijie.nongmo.service;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlbumAutoCheckService {

    @Value("${jsoup.proxy.switch:false}")
    private boolean proxySwitch;

    @Value("${jsoup.proxy.ip:127.0.0.1}")
    private String proxyIp;

    @Value("${jsoup.proxy.port:7890}")
    private int proxyPort;


    public void check(){
        try{
            Document doc = Jsoup
            .connect("https://everia.club/2023/05/06/reona-matsushita-%e6%9d%be%e4%b8%8b%e7%8e%b2%e7%b7%92%e8%8f%9c-flash%e3%83%87%e3%82%b8%e3%82%bf%e3%83%ab%e5%86%99%e7%9c%9f%e9%9b%86%e3%80%80%e3%80%8c%e7%b4%a0%e8%82%8c%e3%81%a7%e3%80%81%e6%97%85-3/")
            .proxy(proxyIp, proxyPort)
            .get();
            log.info("解析到html:{}",doc.title());
            Elements tagElements = doc.getElementsByClass("nv-tags-list");
            log.info("解析到标签:{}", tagElements);
        }catch(Exception exception) {
            log.error("解析文件出现错误:", exception);
        }
    }
}
