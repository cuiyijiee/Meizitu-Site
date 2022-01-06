package me.cuiyijie.nongmo.entity;

import lombok.Data;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:30
 */
@Data
public class Picture {

    private Long id;
    private Long albumId;
    private String url;
    private Integer picIndex;

}
