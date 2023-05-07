package me.cuiyijie.nongmo.job;

import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.nongmo.service.AlbumAutoCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "scheduling", name = "autoCheck", havingValue = "true")
public class AutoCheckJob {

    @Autowired
    private AlbumAutoCheckService albumAutoCheckService;

    @Scheduled(cron = "${scheduling.autoCheckRule}")
    public void autoCheck() {
        albumAutoCheckService.check();
    }
}
