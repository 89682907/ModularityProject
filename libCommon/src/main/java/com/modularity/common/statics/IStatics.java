package com.modularity.common.statics;

import java.io.File;

/**
 * 存放一些静态参数
 * Created by jishen on 2017/12/20.
 */

public interface IStatics {

    String STRING_SEPARATOR = "_";

    String BUILD_TYPE_DEBUG   = "debug";
    String BUILD_TYPE_BETA    = "beta";
    String BUILD_TYPE_RELEASE = "release";

    String CACHE_DIR_NAME    = (Config.BUILD_TYPE.equals(BUILD_TYPE_DEBUG) ? "modularityDev" : Config.BUILD_TYPE.equals(BUILD_TYPE_BETA) ? "modularityBeta" : "modularity") + (Config.DEVELOP ? "Dv" : "");
    String CACHE_DIR         = File.separator + CACHE_DIR_NAME + File.separator;
    String SDCARD_UPDATE_DIR = CACHE_DIR + "update/";
    String SDCARD_LOG_DIR    = CACHE_DIR + "log/";
    String SDCARD_CACHE_DIR  = CACHE_DIR + "cache/";
    String SDCARD_FACE_DIR   = CACHE_DIR + "cache/face/";

}
