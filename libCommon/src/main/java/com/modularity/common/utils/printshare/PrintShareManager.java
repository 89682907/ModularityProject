package com.modularity.common.utils.printshare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.modularity.common.statics.Config;
import com.modularity.common.utils.managers.manager.AppManager;

import java.io.File;

public class PrintShareManager {
    private PrintShareManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void printDocuments(Context mContext, String filePath) {
        if (AppManager.isAppInstalled("com.dynamixsoftware.printershare")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName componentName = new ComponentName("com.dynamixsoftware.printershare",
                    "com.dynamixsoftware.printershare.ActivityPrintDocuments");
            intent.setComponent(componentName);
            if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N
                intent.setData(FileProvider.getUriForFile(mContext, Config.PACKAGE_FIX_NAME + ".fileProvider", new File(filePath)));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {//7.0以下
                intent.setData(Uri.fromFile(new File(filePath)));
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
