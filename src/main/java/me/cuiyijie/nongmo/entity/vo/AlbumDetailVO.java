package me.cuiyijie.nongmo.entity.vo;

import lombok.Data;
import me.cuiyijie.nongmo.entity.Album;
import me.cuiyijie.nongmo.entity.Category;
import me.cuiyijie.nongmo.entity.Picture;
import me.cuiyijie.nongmo.entity.Tag;

import java.util.List;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2022/8/9 14:25
 */
@Data
public class AlbumDetailVO {

    private Album album;

    private Category category;

    private List<Picture> pictureList;

    private List<Tag> tagList;

}
