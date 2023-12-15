package me.cuiyijie.nongmo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:30
 */
@Data
public class Picture implements Serializable {

    private Long id;
    private Long albumId;
    private String url;
    private Integer picIndex;

}
