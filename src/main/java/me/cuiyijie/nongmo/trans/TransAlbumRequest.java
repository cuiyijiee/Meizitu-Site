package me.cuiyijie.nongmo.trans;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: yjcui3
 * @Date: 2022/7/29 14:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransAlbumRequest extends TransBasePageRequest {

    private String query;

}
