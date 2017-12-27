package com.modularity.face.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 图片控制
 */
public class ImageUtil {
    /**
     * 旋转Bitmap
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }
}
