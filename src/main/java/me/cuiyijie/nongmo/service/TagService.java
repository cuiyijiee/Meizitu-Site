package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cuiyijie.nongmo.mapper.TagMapper;
import me.cuiyijie.nongmo.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagService extends ServiceImpl<TagMapper, Tag> {

    public void autoSetTag(long albumId, List<String> tags) {
        List<Tag> existTags = baseMapper.selectAlbumTags(albumId);
        for (String newTag : tags) {
            boolean isExist = existTags.stream().anyMatch(tag -> tag.getTagName().equals(newTag));
            if (!isExist) {
                Tag tag = baseMapper.selectByName(newTag);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTagName(newTag);
                    tag.setCreatedAt(new Date());
                    tag.setEnabled(true);
                    baseMapper.insert(tag);
                }

                baseMapper.addAlbumTag(albumId, tag.getId());
            }
        }
    }

}
