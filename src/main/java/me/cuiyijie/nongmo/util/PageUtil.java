package me.cuiyijie.nongmo.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:41
 */
public class PageUtil {

    public static <T> PageResp<T> convertFromPage(IPage<T> page) {
        return new PageResp<T>(page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                page.getRecords());
    }

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

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Long getCurrent() {
            return current;
        }

        public void setCurrent(Long current) {
            this.current = current;
        }

        public Long getPageSize() {
            return pageSize;
        }

        public void setPageSize(Long pageSize) {
            this.pageSize = pageSize;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public boolean hasPre() {
            return this.current > 1;
        }

        public Long getPre() {
            return this.current - 1;
        }

        public boolean hasNext() {
            return this.current <= Math.ceil(this.total / this.pageSize);
        }

        public Long getNext() {
            return this.current + 1;
        }

    }
}