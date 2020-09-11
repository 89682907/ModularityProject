
package com.modularity.perfection.exception;

public class PerfectionThrowable extends Exception {

    private int    code;
    private String message;

    public PerfectionThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
