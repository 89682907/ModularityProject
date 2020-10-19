package com.modularity.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

/**
 * 相机预览size设置
 */
public class CamParaUtil {
    private static CamParaUtil          sCamPara        = null;
    private        CameraSizeComparator mSizeComparator = new CameraSizeComparator();

    private CamParaUtil() {

    }

    public static CamParaUtil getInstance() {
        if (sCamPara == null) {
            sCamPara = new CamParaUtil();
        }
        return sCamPara;
    }

    public Size getPropPreviewSize(List<Size> list, float th, int minWidth) {
        Collections.sort(list, mSizeComparator);

        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth) && equalRate(s, th)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    public Size getPropPictureSize(List<Size> list, float th, int minWidth) {
        Collections.sort(list, mSizeComparator);
        int i = 0;
        for (Size s : list) {
            if ((s.width >= minWidth) && equalRate(s, th)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;//如果没找到，就选最小的size
        }
        return list.get(i);
    }

    private boolean equalRate(Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        return Math.abs(r - rate) <= 0.03;
    }

    private static class CameraSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            return Integer.compare(lhs.width, rhs.width);
        }

    }

    /**
     * 打印支持的previewSizes
     */
    public void printSupportPreviewSize(Camera.Parameters params) {
        List<Size> previewSizes = params.getSupportedPreviewSizes();
        for (int i = 0; i < previewSizes.size(); i++) {
            Size size = previewSizes.get(i);
        }

    }

    /**
     * 打印支持的pictureSizes
     */
    public void printSupportPictureSize(Camera.Parameters params) {
        List<Size> pictureSizes = params.getSupportedPictureSizes();
        for (int i = 0; i < pictureSizes.size(); i++) {
            Size size = pictureSizes.get(i);
        }
    }

    /**
     * 打印支持的聚焦模式
     */
    public void printSupportFocusMode(Camera.Parameters params) {
        List<String> focusModes = params.getSupportedFocusModes();
        for (String mode : focusModes) {
            Log.i("CamParaUtil", "focusModes--" + mode);
        }
    }
}
