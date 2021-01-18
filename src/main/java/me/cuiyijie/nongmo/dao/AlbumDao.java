package me.cuiyijie.nongmo.dao;

import me.cuiyijie.nongmo.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:38
 */
@Repository
public interface AlbumDao extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {

}
