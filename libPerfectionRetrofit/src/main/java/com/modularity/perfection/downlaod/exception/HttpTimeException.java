package com.modularity.perfection.downlaod.exception;

/**
 * 自定义错误信息，统一处理返回处理
 */
public class HttpTimeException extends RuntimeException {

    private static final int NO_DATA = 0x2;

    public HttpTimeException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public HttpTimeException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换错误数据
     */
    private static String getApiExceptionMessage(int code) {
        String message;
        if (code == NO_DATA) {
            message = "无数据";
        } else {
            message = "error";

        }
        return message;
    }
}

