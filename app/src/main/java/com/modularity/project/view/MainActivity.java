package com.modularity.project.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.community.jetpack.view.JetpackMainActivity;
import com.modularity.common.base.BaseActivity;
import com.modularity.mod_a.view.ModuleAActivity;
import com.modularity.mod_b.ModuleBActivity;
import com.modularity.mvvm.view.MVVMMainActivity;
import com.modularity.project.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_a).setOnClickListener(this);
        findViewById(R.id.btn_b).setOnClickListener(this);
        findViewById(R.id.btn_c).setOnClickListener(this);
        findViewById(R.id.btn_signature).setOnClickListener(this);
        findViewById(R.id.btn_jetpack).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_a) {
            moduleA();
        } else if (i == R.id.btn_b) {
            moduleB();
        } else if (i == R.id.btn_c) {
            moduleC();
        } else if (i == R.id.btn_signature) {
            libSignature();
        } else if (i == R.id.btn_jetpack) {
            modJetpack();
        }
    }

    private void moduleA() {
        startActivity(new Intent(this, ModuleAActivity.class));
    }

    private void moduleB() {
        startActivity(new Intent(this, ModuleBActivity.class));
    }

    private void moduleC() {
        startActivity(new Intent(this, MVVMMainActivity.class));
    }

    private void libSignature() {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    private void modJetpack() {
        startActivity(new Intent(this, JetpackMainActivity.class));
    }
}
