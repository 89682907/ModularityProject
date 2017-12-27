package com.modularity.face.util;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.util.Log;

/**
 * Created by jishen on 2017/7/18.
 * 检测图片中是否有人脸
 */

public class FaceCheckUtil {
    private static final String  sTAG         = "FaceCheckUtil";
    private static final boolean DEBUG_ENABLE = false;

    public static Bitmap genFaceBitmap(Bitmap sourceBitmap) {

        if (!checkBitmap(sourceBitmap, "genFaceBitmap()")) {
            return null;
        }
        Bitmap cacheBitmap = sourceBitmap.copy(Bitmap.Config.RGB_565, false);
        if (DEBUG_ENABLE) {
            Log.i(sTAG, "genFaceBitmap() : source bitmap width - " + cacheBitmap.getWidth() + " , height - " + cacheBitmap.getHeight());
        }
        int cacheWidth = cacheBitmap.getWidth();
        int cacheHeight = cacheBitmap.getHeight();
        if (cacheWidth % 2 != 0) {
            if (0 == cacheWidth - 1) {
                if (DEBUG_ENABLE) {
                    Log.e(sTAG, "genFaceBitmap() : source bitmap width is only 1 , return null.");
                }
                return null;
            }
            final Bitmap localCacheBitmap = Bitmap.createBitmap(cacheBitmap, 0, 0, cacheWidth - 1, cacheHeight);
            cacheBitmap.recycle();
            cacheBitmap = localCacheBitmap;
            --cacheWidth;
            if (DEBUG_ENABLE) {
                Log.i(sTAG, "genFaceBitmap() : source bitmap width - " + cacheBitmap.getWidth() + " , height - " + cacheBitmap.getHeight());
            }
        }
        final FaceDetector.Face[] faces = new FaceDetector.Face[1];
        final int facefound = new FaceDetector(cacheWidth, cacheHeight, 1).findFaces(cacheBitmap, faces);
        if (DEBUG_ENABLE) {
            Log.i(sTAG, "genFaceBitmap() : facefound - " + facefound);
        }
        if (0 == facefound) {
            if (DEBUG_ENABLE) {
                Log.e(sTAG, "genFaceBitmap() : no face found , return null.");
            }
            return null;
        }

        final PointF p = new PointF();
        faces[0].getMidPoint(p);
        if (DEBUG_ENABLE) {
            Log.i(sTAG, "getFaceBitmap() : confidence - " + faces[0].confidence());
            Log.i(sTAG, "genFaceBitmap() : face center x - " + p.x + " , y - " + p.y);
        }
        final int faceX = (int) p.x;
        final int faceY = (int) p.y;
        if (DEBUG_ENABLE) {
            Log.i(sTAG, "genFaceBitmap() : int faceX - " + faceX + " , int faceY - " + faceY);
        }
        int startX, startY, width, height;
        if (faceX <= cacheWidth - faceX) {
            startX = 0;
            width = faceX * 2;
        } else {
            startX = faceX - (cacheWidth - faceX);
            width = (cacheWidth - faceX) * 2;
        }
        if (faceY <= cacheHeight - faceY) {
            startY = 0;
            height = faceY * 2;
        } else {
            startY = faceY - (cacheHeight - faceY);
            height = (cacheHeight - faceY) * 2;
        }

        PointF pf = new PointF();
        faces[0].getMidPoint(pf);
        // 这里的框，参数分别是：左上角的X,Y 右下角的X,Y
        // 也就是左上角（r.left,r.top），右下角( r.right,r.bottom)。
        // 作为定位，确定这个框的格局。
        RectF r = new RectF();
        r.left = pf.x - faces[0].eyesDistance() / 2;
        r.right = pf.x + faces[0].eyesDistance() / 2;
        r.top = pf.y - faces[0].eyesDistance() / 2;
        r.bottom = pf.y + faces[0].eyesDistance() / 2;
        // 画框:对原图进行处理，并在图上显示人脸框。
        float distance = r.right - r.left;
        Log.i(sTAG, "distance:=" + distance);
        if (distance > 50) {
            Bitmap result = Bitmap.createBitmap(cacheBitmap, startX, startY, width, height);
            cacheBitmap.recycle();
            return result;
        }
//        final Bitmap result = Bitmap.createBitmap(cacheBitmap, startX, startY, width, height);
        cacheBitmap.recycle();
        return null;
    }

    private static boolean checkBitmap(final Bitmap bitmap, final String debugInfo) {
        if (null == bitmap || bitmap.isRecycled()) {
            if (DEBUG_ENABLE) {
                Log.e(sTAG, debugInfo + " : check bitmap , is null or is recycled.");
            }
            return false;
        }
        if (0 == bitmap.getWidth() || 0 == bitmap.getHeight()) {
            if (DEBUG_ENABLE) {
                Log.e(sTAG, debugInfo + " : check bitmap , width is 0 or height is 0.");
            }
            return false;
        }
        return true;
    }

}
