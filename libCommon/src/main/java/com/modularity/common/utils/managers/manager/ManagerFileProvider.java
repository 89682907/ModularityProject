package com.modularity.common.utils.managers.manager;

import android.app.Application;

import androidx.core.content.FileProvider;

public class ManagerFileProvider extends FileProvider {

    @Override
    public boolean onCreate() {
        //noinspection ConstantConditions
        Managers.init((Application) getContext().getApplicationContext());
        return true;
    }
}
