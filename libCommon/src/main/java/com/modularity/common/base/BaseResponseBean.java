package com.modularity.common.base;

/**
 * Created by jishen on 2017/1/10.
 */

public class BaseResponseBean {
    private int    code;
    private String message;

    public boolean isSuccess() {
        return 200 == code;//实际情况根据code设置
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
