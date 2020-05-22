package com.modularity.mod_a.bean;

import com.modularity.common.annotation.KeepNotProguard;
import com.modularity.common.base.BaseResponseBean;

/**
 * Created by jishen on 2017/12/25.
 */
@KeepNotProguard
public class ModuleAResponseBean extends BaseResponseBean {
    private int cod;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
