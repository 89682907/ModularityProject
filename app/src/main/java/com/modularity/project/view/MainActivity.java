package com.modularity.project.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.community.jetpack.view.JetpackMainActivity;
import com.modularity.common.base.BaseActivity;
import com.modularity.common.utils.managers.manager.LogManager;
import com.modularity.common.utils.managers.manager.SDCardManager;
import com.modularity.mod_a.view.ModuleAActivity;
import com.modularity.mod_b.ModuleBActivity;
import com.modularity.mvvm.view.MVVMMainActivity;
import com.modularity.project.R;
import com.modularity.project.jsbridage.JsBridgeMainActivity;
import com.modularity.view.CameraActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String tag = "jishen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_a).setOnClickListener(this);
        findViewById(R.id.btn_b).setOnClickListener(this);
        findViewById(R.id.btn_c).setOnClickListener(this);
        findViewById(R.id.btn_signature).setOnClickListener(this);
        findViewById(R.id.btn_jetpack).setOnClickListener(this);
        findViewById(R.id.btn_jsbridge).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_tts).setOnClickListener(this);
        test();
    }

    private void test() {
        LogManager.iTag(tag, SDCardManager.getSDCardExternalCacheDir(this));
        LogManager.iTag(tag, SDCardManager.getSDCardExternalFilesDir(this, ""));
        LogManager.iTag(tag, SDCardManager.getSDCardExternalFilesDir(this, "audio"));

        View loadView = findViewById(R.id.load1);
        loadView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        View loadView2 = findViewById(R.id.load2);
        loadView2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_2));
        View loadView3 = findViewById(R.id.load3);
        loadView3.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_3));
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
        } else if (i == R.id.btn_jsbridge) {
            modJsBridge();
        } else if (i == R.id.btn_camera) {
            libCamera();
        } else if (i == R.id.btn_tts) {
            TTSManager tts = new TTSManager(this);
            tts.startTTS("我乃常山赵子龙");
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

    private void modJsBridge() {
        startActivity(new Intent(this, JsBridgeMainActivity.class));
    }

    private void libCamera() {
        startActivity(new Intent(this, CameraActivity.class));
    }
}
