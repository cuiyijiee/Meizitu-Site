package me.cuiyijie.nongmo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:48
 */

@Data
public class Category {

    private Long id;
    private String name;
    private Integer showOrder;
    private LocalDateTime createdAt;

}
