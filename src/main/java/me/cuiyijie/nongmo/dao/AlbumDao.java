package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:38
 */
@Repository
public interface AlbumDao extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {


    @Query(nativeQuery = true, value = "select * from album where title like %:title% order by id limit 100")
    List<Album> findByTitleLike(@Param("title") String title);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update album set view_num = view_num + 1 where id = :id")
    int updateAllView(@Param("id") Long id);

}
