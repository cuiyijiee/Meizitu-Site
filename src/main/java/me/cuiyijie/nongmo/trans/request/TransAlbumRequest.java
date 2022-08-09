package me.cuiyijie.nongmo.trans.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.cuiyijie.nongmo.enums.AlbumOrderEnum;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/7/29 14:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransAlbumRequest extends TransBasePageRequest {

    private Integer id;

    private String query;

    private AlbumOrderEnum orderBy;

    private Integer category;

}
