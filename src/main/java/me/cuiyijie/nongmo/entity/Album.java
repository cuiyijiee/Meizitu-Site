package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.OffsetDateTime;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:47
 */
@Data
public class Album {

    private Long id;
    private String originId;
    private String coverUrl;
    private String albumUrl;
    private String title;
    private Long category;
    private Integer viewNum;
    @TableLogic
    private Boolean enabled;
    private OffsetDateTime createdAt;

}
