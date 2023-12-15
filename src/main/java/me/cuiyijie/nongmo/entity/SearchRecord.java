package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SearchRecord {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String searchKey;

    private String ip;

    private Date createTime;

}
