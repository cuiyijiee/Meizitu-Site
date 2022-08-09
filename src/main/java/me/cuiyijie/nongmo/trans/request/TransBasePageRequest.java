package me.cuiyijie.nongmo.trans.request;

import lombok.Data;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/7/29 10:46
 */
@Data
public class TransBasePageRequest {

    private Integer current = 0;
    private Integer pageSize = 10;

}
