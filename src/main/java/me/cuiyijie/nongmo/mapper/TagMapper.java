package me.cuiyijie.nongmo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.cuiyijie.nongmo.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {


    Tag selectByName(@Param("tagName") String tagName);


    List<Tag> selectAlbumTags(@Param("albumId") long albumId);


    int addAlbumTag(@Param("albumId") long albumId,@Param("tagId") long tagId);

}
