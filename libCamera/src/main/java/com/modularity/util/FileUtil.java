package com.modularity.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片保存控制类
 */
public class FileUtil {
    private static final String sTAG        = "FileUtil";
    private static final File   sParentPath = Environment.getExternalStorageDirectory();
    private static       String sStoragePath;

    /**
     * 初始化保存路径
     */
    private static String initPath() {
        if (TextUtils.isEmpty(sStoragePath)) {
            sStoragePath = sStoragePath + "camera";
        }
        File f = new File(sStoragePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        return sStoragePath;
    }

    /**
     * 保存Bitmap到sdcard
     */
    public static String saveBitmap(Bitmap b) {
        try {
            String jpegName = initPath() + System.currentTimeMillis() + ".jpg";
            FileOutputStream fOut = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fOut);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            fOut.close();
            Log.i(sTAG, "saveBitmap成功:" + jpegName);
            return jpegName;
        } catch (IOException e) {
            Log.i(sTAG, "saveBitmap失败\n" + e.getMessage());
            return "";
        }

    }
}
