package com.modularity.common.statics;

import com.modularity.common.utils.utilcode.util.SDCardUtils;

import java.io.File;

import static com.modularity.common.statics.Config.APP_NAME;

/**
 * 存放一些静态参数
 * Created by jishen on 2017/12/20.
 */

public interface IStatics {

    String BUILD_TYPE_DEBUG   = "debug";
    String BUILD_TYPE_BETA    = "beta";
    String BUILD_TYPE_RELEASE = "release";

    interface IPathStatics {
        String SDCARD_PATH = SDCardUtils.getSDCardPathByEnvironment();
        String APP_DIR     = SDCARD_PATH + File.separator + APP_NAME + File.separator;
        String APK_DIR     = APP_DIR + "apk/";
        String LOG_DIR     = APP_DIR + "log/";
        String CACHE_DIR   = APP_DIR + "cache/";
    }

}
