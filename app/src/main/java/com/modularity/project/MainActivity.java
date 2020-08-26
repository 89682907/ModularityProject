package com.modularity.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.modularity.mod_a.view.ModuleAActivity;
import com.modularity.mod_b.ModuleBActivity;
import com.modularity.mvvm.view.MVVMMainActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mBtnA = findViewById(R.id.btn_a);
        mBtnA.setOnClickListener(this);
        Button mBtnB = findViewById(R.id.btn_b);
        mBtnB.setOnClickListener(this);
        Button mBtnC = findViewById(R.id.btn_c);
        mBtnC.setOnClickListener(this);
        Button mBtnSignature = findViewById(R.id.btn_signature);
        mBtnSignature.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_a) {
            moduleA();
        } else if (i == R.id.btn_b) {
            moduleB();
        }else if (i == R.id.btn_c) {
            moduleC();
        } else if (i == R.id.btn_signature) {
            libSignature();
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
}
