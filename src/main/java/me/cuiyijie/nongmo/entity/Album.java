package me.cuiyijie.nongmo.entity;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 19:47
 */
@Entity
@Table(name = "album")
public class Album {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originId;

    @Column
    private String coverUrl;

    @Column
    private String albumUrl;

    @Column
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @Column
    private Integer viewNum;

    @Column
    private OffsetDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlEncodedTitle() throws UnsupportedEncodingException {
        return URLEncoder.encode(title,"UTF-8");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", originId='" + originId + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", albumUrl='" + albumUrl + '\'' +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", viewNum=" + viewNum +
                ", createdAt=" + createdAt +
                '}';
    }
}
