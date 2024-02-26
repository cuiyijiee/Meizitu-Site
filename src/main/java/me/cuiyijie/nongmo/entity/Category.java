package me.cuiyijie.nongmo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:48
 */

@Data
public class Category implements Serializable {

    private Long id;
    private String name;
    private Integer showOrder;
    private Date createdAt;
    private Boolean enabled;

}
 