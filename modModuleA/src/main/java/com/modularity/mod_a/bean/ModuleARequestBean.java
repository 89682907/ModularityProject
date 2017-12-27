package com.modularity.mod_a.bean;

import com.modularity.common.base.BaseRequestBean;

/**
 * Created by jishen on 2017/12/25.
 */

public class ModuleARequestBean extends BaseRequestBean {
    private String mode;
    private String units;
    private int cnt;
    private String APPID;
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
