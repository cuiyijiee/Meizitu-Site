package me.cuiyijie.nongmo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.cuiyijie.nongmo.dao.SearchRecordDao;
import me.cuiyijie.nongmo.entity.SearchRecord;
import org.springframework.stereotype.Service;

@Service
public class SearchRecordService extends ServiceImpl<SearchRecordDao, SearchRecord> {

    public void insertNewSearchRecord(String searchKey, String ip) {
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setSearchKey(searchKey);
        searchRecord.setIp(ip);
        baseMapper.insert(searchRecord);
    }

}
