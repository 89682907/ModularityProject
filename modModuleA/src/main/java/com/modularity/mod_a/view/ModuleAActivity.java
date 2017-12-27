package com.modularity.mod_a.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.statics.IRouteStatics;
import com.modularity.mod_a.R;
import com.modularity.mod_a.bean.ModuleAResponseBean;
import com.modularity.mod_a.presenter.ModuleAPresenterImpl;

@Route(path = IRouteStatics.MODULE_A_ACTIVITY)
public class ModuleAActivity extends BaseActivity implements IModuleAView {

    private ModuleAPresenterImpl mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_a);
        request();
        initButton();
    }

    private void initButton(){
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(view -> ARouter.getInstance().build(IRouteStatics.MODULE_B_ACTIVITY).navigation());
    }

    private void request() {
        mPresenter = new ModuleAPresenterImpl(this);
        mPresenter.attachView(this);
        mPresenter.request();
    }

    @Override
    public void showLoad() {
        Toast.makeText(this, "showLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoad() {
        Toast.makeText(this, "dismissLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequest(boolean success, ModuleAResponseBean bean) {
        if (success) {
            Toast.makeText(this, bean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
