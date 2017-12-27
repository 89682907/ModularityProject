package com.modularity.common.base;

/**
 * Created by jishen on 2017/1/19.
 */

public abstract class BaseEvent {
    private String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
