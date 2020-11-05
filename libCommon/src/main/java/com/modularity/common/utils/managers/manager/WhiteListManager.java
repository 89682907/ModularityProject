package com.modularity.common.utils.managers.manager;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class WhiteListManager {

    private WhiteListManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void requestWhiteSetting(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isIgnoringBatteryOptimizations(context)) {
                requestIgnoreBatteryOptimizations(context);
            } else {
                LogManager.i("已加入白名单");
            }
        }
    }

    public static void requestRoomWhiteSetting(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (RomManager.isHuawei()) {
                    goHuaWeiSetting(context);
                } else if (RomManager.isMeizu()) {
                    goMeiZuSetting(context);
                } else if (RomManager.isOppo()) {
                    goOPPOSetting(context);
                } else if (RomManager.isSamsung()) {
                    goSamsungSetting(context);
                } else if (RomManager.isSmartisan()) {
                    goSmartisanSetting(context);
                } else if (RomManager.isVivo()) {
                    goVIVOSetting(context);
                } else if (RomManager.isXiaomi()) {
                    goXiaoMiSetting(context);
                } else if (RomManager.isLeeco()) {
                    goLeTvSetting(context);
                } else {
                    requestWhiteSetting(context);
                }
            } catch (Exception e) {
                requestIgnoreBatteryOptimizations(context);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return isIgnoring;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestIgnoreBatteryOptimizations(Context context) {
        try {
            @SuppressLint("BatteryLife")
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转到指定应用的首页
     */
    private static void showActivity(Context context, @NonNull String packageName) throws Exception {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private static void showActivity(Context context, @NonNull String packageName, @NonNull String activityDir) throws Exception {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 华为
     */
    public static void goHuaWeiSetting(Context context) throws Exception {
        try {
            showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity(context, "com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }


    /**
     * 小米
     */
    public static void goXiaoMiSetting(Context context) throws Exception {
        showActivity(context, "com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }

    /**
     * OPPO
     */
    public static void goOPPOSetting(Context context) throws Exception {
        try {
            showActivity(context, "com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity(context, "com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity(context, "com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity(context, "com.coloros.safecenter");
                }
            }
        }
    }

    /**
     * VIVO
     */
    public static void goVIVOSetting(Context context) throws Exception {
        showActivity(context, "com.iqoo.secure");
    }

    /**
     * 魅族
     */
    public static void goMeiZuSetting(Context context) throws Exception {
        showActivity(context, "com.meizu.safe");
    }

    /**
     * 三星
     */
    public static void goSamsungSetting(Context context) throws Exception {
        try {
            showActivity(context, "com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity(context, "com.samsung.android.sm");
        }
    }

    /**
     * 乐视
     */
    public static void goLeTvSetting(Context context) throws Exception {
        showActivity(context, "com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity");
    }


    /**
     * 锤子
     */
    public static void goSmartisanSetting(Context context) throws Exception {
        showActivity(context, "com.smartisanos.security");
    }

}
