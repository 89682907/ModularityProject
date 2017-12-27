package com.modularity.mod_a.view;

import com.modularity.common.base.IBaseView;
import com.modularity.mod_a.bean.ModuleAResponseBean;

/**
 * Created by jishen on 2017/12/25.
 */

public interface IModuleAView extends IBaseView{
    void showLoad();
    void dismissLoad();
    void onRequest(boolean success,ModuleAResponseBean bean);
}
