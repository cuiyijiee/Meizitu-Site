package me.cuiyijie.nongmo.entity;

import javax.persistence.*;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:30
 */
@Entity
@Table(name = "picture")
public class Picture {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long albumId;

    @Column
    private String url;

    @Column
    private Integer index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", url='" + url + '\'' +
                ", index=" + index +
                '}';
    }
}
