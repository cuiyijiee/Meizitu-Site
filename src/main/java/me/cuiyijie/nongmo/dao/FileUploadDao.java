package me.cuiyijie.nongmo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cuiyijie.nongmo.entity.FileUpload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadDao extends BaseMapper<FileUpload> {


    FileUpload selectByMd5(@Param("fileMd5") String fileMd5);


}
