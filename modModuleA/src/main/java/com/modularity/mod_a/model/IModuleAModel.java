package com.modularity.mod_a.model;

import com.modularity.common.base.IBaseModel;
import com.modularity.mod_a.bean.ModuleAResponseBean;

/**
 * Created by jishen on 2017/12/25.
 */

public interface IModuleAModel extends IBaseModel {

    void request();

    interface IModuleAModelListener extends IBaseModelListener{
        void showLoad();
        void dismissLoad();
        void onRequest(boolean success,ModuleAResponseBean bean);
    }
}
