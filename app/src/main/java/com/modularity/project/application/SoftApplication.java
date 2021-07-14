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
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;


/**
 * 应用运行崩溃后，由此捕获异常，上报Log
 */
public class SoftApplication extends Application {

    public void onCreate() {
        super.onCreate();
        initUE();
        initRouter();
        initConfig();
        tbs();
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

    private void tbs() {
// 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("jishen", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.d("jishen", "onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(
                new TbsListener() {
                    @Override
                    public void onDownloadFinish(int i) {
                        Log.d("jishen", "onDownloadFinish -->下载X5内核完成：" + i);
                    }

                    @Override
                    public void onInstallFinish(int i) {
                        Log.d("jishen", "onInstallFinish -->安装X5内核进度：" + i);
                    }

                    @Override
                    public void onDownloadProgress(int i) {
                        Log.d("jishen", "onDownloadProgress -->下载X5内核进度：" + i);
                    }
                });
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