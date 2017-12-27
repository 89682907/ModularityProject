package com.modularity.mod_b;

import android.os.Bundle;
import android.widget.Button;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.statics.IRouteStatics;

@Route(path = IRouteStatics.MODULE_B_ACTIVITY)
public class ModuleBActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_b);
        initButton();
    }

    private void initButton(){
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(view -> ARouter.getInstance().build(IRouteStatics.LIB_FACE_ACTIVITY).navigation());
    }
}
