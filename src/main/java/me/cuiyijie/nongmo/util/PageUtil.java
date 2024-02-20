package me.cuiyijie.nongmo.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:41
 */
public class PageUtil {

    public static <T> PageResp<T> convertFromPage(IPage<T> page) {
        return new PageResp<>(page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                page.getRecords());
    }

    @Setter
    @Getter
    public static class PageResp<T> {

        public Long total;
        public Long current;
        public Long pageSize;
        public List<T> data;


        public PageResp(Long total, Long current, Long pageSize, List<T> data) {
            this.total = total;
            this.current = current;
            this.pageSize = pageSize;
            this.data = data;
        }

        public boolean hasPre() {
            return this.current > 1;
        }

        public Long getPre() {
            return this.current - 1;
        }

        public boolean hasNext() {
            return this.current < Math.ceil((double) this.total / this.pageSize);
        }

        public Long getNext() {
            return this.current + 1;
        }

        public Long getTotalPage(){
            return new Double(Math.ceil((double) this.total / this.pageSize)).longValue();
        }
    }
}