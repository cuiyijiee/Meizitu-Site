package me.cuiyijie.nongmo.transfer.response;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: cyj976655@gmail.com
 * @Date: 2021/12/28 14:20
 */

@Data
public class TransBaseResponse {
    private String code = "-1";
    private String msg;
    private String warnCode;
    private String warnMsg;
    private Object obj;
    private List<?> list;

    public boolean isSucc() {
        return StringUtils.trimWhitespace(this.code).equals("0");
    }

    public TransBaseResponse() {
    }
}
