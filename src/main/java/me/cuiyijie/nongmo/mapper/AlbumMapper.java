package me.cuiyijie.nongmo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.cuiyijie.nongmo.entity.Album;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:38
 */
@Repository
public interface AlbumMapper extends BaseMapper<Album> {

    List<Album> findAll(@Param("item") Album album);

    List<Album> findByTitleLike(@Param("title") String title);

    Integer addViewNum(@Param("id") Long id);

    IPage<Album> pageFindByTag(IPage<Album> page, @Param("tagId") long tagId);

    List<Album> findByRandom(@Param("limit") int limit);
}
