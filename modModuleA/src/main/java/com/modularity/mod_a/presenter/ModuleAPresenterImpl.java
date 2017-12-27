package com.modularity.mod_a.presenter;

import android.content.Context;

import com.modularity.common.base.IBaseView;
import com.modularity.mod_a.bean.ModuleAResponseBean;
import com.modularity.mod_a.model.IModuleAModel;
import com.modularity.mod_a.model.ModuleAModelImpl;
import com.modularity.mod_a.view.IModuleAView;

/**
 * Created by jishen on 2017/12/25.
 */

public class ModuleAPresenterImpl implements IModuleAPresenter, IModuleAModel.IModuleAModelListener {

    private ModuleAModelImpl mModel;
    private IModuleAView     mView;

    public ModuleAPresenterImpl(Context context) {
        mModel = new ModuleAModelImpl(context, this);
    }


    @Override
    public void request() {
        mModel.request();
    }

    @Override
    public void showLoad() {
        if (mView != null) {
            mView.showLoad();
        }
    }

    @Override
    public void dismissLoad() {
        if (mView != null) {
            mView.dismissLoad();
        }
    }

    @Override
    public void onRequest(boolean success, ModuleAResponseBean bean) {
        if (mView != null) {
            mView.onRequest(success, bean);
        }
    }

    @Override
    public void attachView(IBaseView mView) {
        this.mView = (IModuleAView) mView;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mModel != null) {
            mModel.destroy();
        }
    }
}
