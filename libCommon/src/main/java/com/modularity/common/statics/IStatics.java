package com.modularity.common.statics;

import com.modularity.common.utils.managers.manager.AppManager;
import com.modularity.common.utils.managers.manager.SDCardManager;

import java.io.File;

/**
 * 存放一些静态参数
 * Created by jishen on 2017/12/20.
 */

public interface IStatics {

    String BUILD_TYPE_DEBUG   = "debug";
    String BUILD_TYPE_BETA    = "beta";
    String BUILD_TYPE_RELEASE = "release";

    interface IPathStatics {
        String SDCARD_PATH = SDCardManager.getSDCardPathByEnvironment();
        String APP_DIR     = SDCARD_PATH + File.separator + AppManager.getAppName() + File.separator;
        String APK_DIR     = APP_DIR + "apk/";
        String LOG_DIR     = APP_DIR + "log/";
        String CACHE_DIR   = APP_DIR + "cache/";
    }

}
