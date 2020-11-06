package com.modularity.project.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.modularity.common.statics.Config;
import com.modularity.common.statics.IStatics;
import com.modularity.common.utils.managers.manager.FileIOManager;
import com.modularity.common.utils.managers.manager.LogManager;
import com.modularity.common.utils.managers.manager.Managers;
import com.modularity.project.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * 应用运行崩溃后，由此捕获异常，上报Log
 */
public class SoftApplication extends Application {

    public void onCreate() {
        super.onCreate();
        initUE();
        initRouter();
        initConfig();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Managers.init(this);
    }


    private void initConfig() {
        Config.DEBUG = BuildConfig.DEBUG;
        Config.BUILD_TYPE = BuildConfig.BUILD_TYPE;
        Config.DEVELOP = BuildConfig.DEVELOP;
        Config.VERSION_CODE = BuildConfig.VERSION_CODE;
        Config.VERSION_NAME = BuildConfig.VERSION_NAME;
        Config.APP_NAME = BuildConfig.APP_NAME;
        Config.PACKAGE_FIX_NAME = BuildConfig.PACKAGE_FIX_NAME;
        if (BuildConfig.BUILD_TYPE.equals(IStatics.BUILD_TYPE_DEBUG)) {
            initDebugConfig();
        } else if (BuildConfig.BUILD_TYPE.equals(IStatics.BUILD_TYPE_BETA)) {
            initBetaConfig();
        } else if (BuildConfig.BUILD_TYPE.equals(IStatics.BUILD_TYPE_RELEASE)) {
            initReleaseConfig();
        }
    }

    private void initDebugConfig() {
        LogManager.getConfig().setLogSwitch(true);
    }

    private void initBetaConfig() {
        LogManager.getConfig().setLogSwitch(true);
    }

    private void initReleaseConfig() {
        LogManager.getConfig().setLogSwitch(false || BuildConfig.DEVELOP);

    }


    private void initUE() {
        UEHandler ueHandler = new UEHandler();
        // 设置异常处理实例
        Thread.setDefaultUncaughtExceptionHandler(ueHandler);
    }

    private void initRouter() {
        if (BuildConfig.DEVELOP || IStatics.BUILD_TYPE_DEBUG.endsWith(BuildConfig.BUILD_TYPE) || IStatics.BUILD_TYPE_BETA.endsWith(BuildConfig.BUILD_TYPE)) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }


    /**
     * 捕获系统异常
     */
    private static class UEHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {
            /* 以下为将捕获的异常信息，格式化输出到文件中 */
            String info = null;
            ByteArrayOutputStream baos = null;
            PrintStream printStream = null;
            try {
                baos = new ByteArrayOutputStream();
                printStream = new PrintStream(baos);
                ex.printStackTrace(printStream);
                byte[] data = baos.toByteArray();
                info = new String(data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (printStream != null) {
                        printStream.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            long threadId = thread.getId();
            String tag = "ANDROID_LAB";
            Log.e(tag, "Thread.getName()=" + thread.getName() + " id=" + threadId + " state=" + thread.getState());
            Log.e(tag, "Error[" + info + "]");
            Log.e(tag, "sdcard =>" + Environment.getExternalStorageState());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // sd卡是否挂载
                FileIOManager.writeFileFromString(IStatics.IPathStatics.LOG_DIR + "log.txt", info, true);
                // softApp.startService(new Intent(softApp,
                // UploadService.class));
                // 上传log
//				PublicUtils.uploadLog(SoftApplication.this, "log");
            }
            // kill App Progress
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}