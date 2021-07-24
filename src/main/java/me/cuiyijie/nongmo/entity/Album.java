package me.cuiyijie.nongmo.entity;

import lombok.Data;

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
    private OffsetDateTime createdAt;
}
