package com.modularity.common.utils.utilcode.util;

import android.app.Application;

import androidx.core.content.FileProvider;

public class UtilsFileProvider extends FileProvider {

    @Override
    public boolean onCreate() {
        //noinspection ConstantConditions
        Utils.init((Application) getContext().getApplicationContext());
        return true;
    }
}
