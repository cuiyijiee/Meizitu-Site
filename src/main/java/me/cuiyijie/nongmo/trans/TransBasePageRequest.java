package me.cuiyijie.nongmo.trans;

import lombok.Data;

/**
 * @Author: yjcui3
 * @Date: 2022/7/29 10:46
 */
@Data
public class TransBasePageRequest {

    private Integer current = 0;
    private Integer pageSize = 10;

}
