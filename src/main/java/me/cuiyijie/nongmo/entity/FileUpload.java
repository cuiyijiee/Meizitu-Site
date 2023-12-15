package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class FileUpload implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String originFileName;

    private String fileMd5;

    private String objectName;

    private Date createdAt;

    private Date updatedAt;

}
