package me.cuiyijie.nongmo.service;

import me.cuiyijie.nongmo.dao.TagDao;
import me.cuiyijie.nongmo.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagDao tagDao;

    public void autoSetTag(long albumId, List<String> tags) {
        List<Tag> existTags = tagDao.selectAlbumTags(albumId);
        for (int index = 0; index < tags.size(); index++) {
            String newTag = tags.get(index);
            boolean isExist = existTags.stream().anyMatch(tag -> tag.getTagName().equals(newTag));
            if (!isExist) {
                Tag tag = tagDao.selectByName(newTag);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTagName(newTag);
                    tag.setCreatedAt(LocalDateTime.now());
                    tag.setEnabled(true);
                    tagDao.insert(tag);
                }

                tagDao.addAlbumTag(albumId, tag.getId());
            }
        }
    }

}
