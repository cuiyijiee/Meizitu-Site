package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tag {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tagName;

    private boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
