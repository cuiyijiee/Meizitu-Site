package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Tag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tagName;

    private boolean enabled;

    private Date createdAt;

    private Date updatedAt;
}
