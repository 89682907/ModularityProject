package com.modularity.common.base;

/**
 * Created by jishen on 2017/12/20.
 */

public interface IBasePresenter {
    void attachView(IBaseView mView);
    void detachView();
}
