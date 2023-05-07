package me.cuiyijie.nongmo.service;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.nongmo.dao.TagDao;
import me.cuiyijie.nongmo.entity.vo.AlbumVO;
import me.cuiyijie.nongmo.trans.request.TransAlbumRequest;
import me.cuiyijie.nongmo.util.PageUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AlbumAutoCheckService {

    @Value("${jsoup.proxy.switch:false}")
    private boolean proxySwitch;

    @Value("${jsoup.proxy.ip:127.0.0.1}")
    private String proxyIp;

    @Value("${jsoup.proxy.port:7890}")
    private int proxyPort;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TagService tagService;

    public void check() {
        int index = 0;
        int pageSize = 10;
        TransAlbumRequest transAlbumRequest = new TransAlbumRequest();
        transAlbumRequest.setCurrent(index);
        transAlbumRequest.setPageSize(pageSize);
        PageUtil.PageResp<AlbumVO> albums = albumService.listAlbum(transAlbumRequest);
        while (index <= albums.getTotalPage()) {
            log.info("当前页数:{},总页数:{}", index, albums.getTotalPage());
            for (AlbumVO albumVO : albums.getData()) {
                //先请求head进行判断当前图片集是否已经失效
                String originAlbumUrl = albumVO.getAlbumUrl();
                String coverUrl = albumVO.getCoverUrl();
                try {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
                    HttpURLConnection coverConnection = proxySwitch ? (HttpURLConnection) new URL(coverUrl).openConnection(proxy) :
                            (HttpURLConnection) new URL(coverUrl).openConnection();
                    coverConnection.setRequestMethod("HEAD");
                    boolean isOk = coverConnection.getResponseCode() == HttpStatus.OK.value();
                    if (!isOk) {
                        albumVO.setEnabled(false);
                        albumService.disableAlbum(albumVO.getId());
                    } else {
                        Connection albumRequestConnection = Jsoup.connect(originAlbumUrl);
                        //是否设置代理
                        if (proxySwitch) {
                            albumRequestConnection.proxy(proxyIp, proxyPort);
                        }
                        Document doc = albumRequestConnection.get();
                        Elements tagElements = doc.selectXpath("//*[@class=\"nv-tags-list\"]/a");
                        List<String> tags = tagElements.stream().map(Element::text).map(tag -> {
                            if (tag.startsWith("[") && tag.endsWith("]")) {
                                tag = tag.substring(1);
                                tag = tag.substring(0, tag.length() - 1);
                            }
                            return tag;
                        }).collect(Collectors.toList());
                        log.info("解析到标签:{}", tags);
                        tagService.autoSetTag(albumVO.getId(), tags);
                    }
                } catch (Exception exception) {
                    log.error("解析图片集【" + albumVO.getId() + "】：出现出错误：", exception);
                }
            }
            transAlbumRequest.setCurrent(index++);
            albums = albumService.listAlbum(transAlbumRequest);
        }
    }
}
