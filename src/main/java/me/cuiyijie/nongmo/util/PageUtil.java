package me.cuiyijie.nongmo.util;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author cyj976655@gmail.com
 * @date 2021/1/10 21:41
 */
public class PageUtil {

    public static PageResp convertFromPage(Page page) {
        return new PageResp(page.getTotalElements(), page.getPageable().getPageNumber(), page.getPageable().getPageSize(), page.getContent());
    }

    public static class PageResp<T> {

        public Long total;
        public Integer current;
        public Integer pageSize;
        public List<T> data;


        public PageResp(Long total, Integer current, Integer pageSize, List<T> data) {
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

        public Integer getCurrent() {
            return current;
        }

        public void setCurrent(Integer current) {
            this.current = current;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public boolean hasPre() {
            return this.current > 0;
        }

        public Integer getPre() {
            return this.current;
        }

        public boolean hasNext() {
            return this.current < Math.ceil(this.total / this.pageSize);
        }

        public Integer getNext() {
            return this.current + 2;
        }

    }
}