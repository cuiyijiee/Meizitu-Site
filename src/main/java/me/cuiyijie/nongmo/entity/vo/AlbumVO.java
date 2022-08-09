package me.cuiyijie.nongmo.entity.vo;

import lombok.Data;
import me.cuiyijie.nongmo.entity.Album;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/8/9 16:00
 */
@Data
public class AlbumVO extends Album {

    /**
     * 图片数量
     */
    private Integer pictureCount;

}
