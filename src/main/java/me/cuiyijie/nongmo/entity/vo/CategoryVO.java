package me.cuiyijie.nongmo.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cuiyijie.nongmo.entity.Category;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/8/9 16:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryVO extends Category {

    private Long albumCount;

}
