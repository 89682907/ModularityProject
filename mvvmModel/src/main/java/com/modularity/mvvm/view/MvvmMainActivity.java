package com.modularity.mvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.statics.IRouteStatics;
import com.modularity.mvvm.R;
import com.modularity.mvvm.databinding.MvvmMainActivityBinding;
import com.modularity.mvvm.viewModel.QueryWeatherViewModel;

@Route(path = IRouteStatics.MODULE_MVVM_ACTIVITY)
public class MvvmMainActivity extends BaseActivity {

    // ViewModel
    private QueryWeatherViewModel   mViewModel;
    // DataBinding
    private MvvmMainActivityBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_main_activity);
        // 创建ViewModel
        mViewModel = new QueryWeatherViewModel(this);
        // 绑定View和ViewModel
        mDataBinding.setViewModel(mViewModel);
    }
}
