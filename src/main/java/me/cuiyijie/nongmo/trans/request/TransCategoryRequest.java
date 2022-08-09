package me.cuiyijie.nongmo.trans.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/7/29 10:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransCategoryRequest extends TransBasePageRequest {

    private String query;

}
