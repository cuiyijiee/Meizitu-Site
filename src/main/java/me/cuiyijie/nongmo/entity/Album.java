package me.cuiyijie.nongmo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:47
 */
@Data
public class Album implements Serializable {

    private Long id;
    private String originId;
    private String coverUrl;
    private String albumUrl;
    private String title;
    private Long category;
    private Integer viewNum;
    @TableLogic
    private Boolean enabled;
    private Date createdAt;
    private Date updatedAt;

}
