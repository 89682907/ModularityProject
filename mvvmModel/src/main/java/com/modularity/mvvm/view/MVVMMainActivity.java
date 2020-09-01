package com.modularity.mvvm.view;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.statics.IRouteStatics;
import com.modularity.mvvm.R;
import com.modularity.mvvm.databinding.MvvmMainActivityBinding;
import com.modularity.mvvm.viewModel.QueryWeatherViewModel;

@Route(path = IRouteStatics.MODULE_MVVM_ACTIVITY)
public class MVVMMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    private void initDataBinding() {
        // DataBinding
        MvvmMainActivityBinding mDataBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_main_activity);
        // 创建ViewModel
        QueryWeatherViewModel mViewModel = new QueryWeatherViewModel(this);
        mViewModel.loading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i("jishen", "propertyId:" + propertyId);
            }
        });
        // 绑定View和ViewModel
        mDataBinding.setViewModel(mViewModel);
    }
}
