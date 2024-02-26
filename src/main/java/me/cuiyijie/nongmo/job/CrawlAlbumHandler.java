package me.cuiyijie.nongmo.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawlAlbumHandler {

    @XxlJob("CrawlAlbum")
    public void crawlAlbum() {



    }
}
