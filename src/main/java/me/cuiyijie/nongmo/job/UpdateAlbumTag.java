package me.cuiyijie.nongmo.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.nongmo.service.AlbumAutoCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateAlbumTag {

    @Autowired
    private AlbumAutoCheckService albumAutoCheckService;

    @XxlJob("UpdateAlbumTag")
    public void updateAlbumTag() {
        String params = XxlJobHelper.getJobParam();
        XxlJobHelper.log("【UpdateAlbumTag】任务开始：" + params);
        albumAutoCheckService.check();
        XxlJobHelper.log("【UpdateAlbumTag】任务结束!");
    }
}
