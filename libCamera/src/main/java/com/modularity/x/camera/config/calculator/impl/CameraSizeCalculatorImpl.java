package com.modularity.x.camera.config.calculator.impl;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.modularity.common.utils.managers.manager.LogManager;
import com.modularity.x.camera.config.calculator.CameraSizeCalculator;
import com.modularity.x.camera.config.size.AspectRatio;
import com.modularity.x.camera.config.size.Size;
import com.modularity.x.camera.enums.MediaQuality;
import com.modularity.x.camera.util.CameraHelper;

import java.util.List;

public class CameraSizeCalculatorImpl implements CameraSizeCalculator {

    private static final String TAG = "CameraSizeCalculator";

    private List<Size>  previewSizes;
    private List<Size>  pictureSizes;
    private List<Size>  videoSizes;
    private AspectRatio expectAspectRatio;
    @Nullable
    private Size        expectSize;
    @MediaQuality
    private int         mediaQuality;

    private SparseArray<Size> outPictureSizes        = new SparseArray<>();
    private SparseArray<Size> outVideoSizes          = new SparseArray<>();
    private SparseArray<Size> outPicturePreviewSizes = new SparseArray<>();
    private SparseArray<Size> outVideoPreviewSizes   = new SparseArray<>();

    @Override
    public void init(@NonNull AspectRatio expectAspectRatio,
                     @Nullable Size expectSize,
                     @MediaQuality int mediaQuality,
                     @NonNull List<Size> previewSizes,
                     @NonNull List<Size> pictureSizes,
                     @NonNull List<Size> videoSizes) {
        this.expectAspectRatio = expectAspectRatio;
        this.expectSize = expectSize;
        this.mediaQuality = mediaQuality;
        this.previewSizes = previewSizes;
        this.pictureSizes = pictureSizes;
        this.videoSizes = videoSizes;
    }

    @Override
    public void changeExpectAspectRatio(@NonNull AspectRatio expectAspectRatio) {
//        LogManager.iTag(TAG, "changeExpectAspectRatio : cache cleared");
        this.expectAspectRatio = expectAspectRatio;
        outPictureSizes.clear();
        outPicturePreviewSizes.clear();
        outVideoSizes.clear();
        outVideoPreviewSizes.clear();
    }

    @Override
    public void changeExpectSize(@Nullable Size expectSize) {
//        LogManager.iTag(TAG, "changeExpectSize : cache cleared");
        this.expectSize = expectSize;
        outPictureSizes.clear();
        outPicturePreviewSizes.clear();
        outVideoSizes.clear();
        outVideoPreviewSizes.clear();
    }

    @Override
    public void changeMediaQuality(int mediaQuality) {
//        LogManager.iTag(TAG, "changeMediaQuality : cache cleared");
        this.mediaQuality = mediaQuality;
        outPictureSizes.clear();
        outPicturePreviewSizes.clear();
        outVideoSizes.clear();
        outVideoPreviewSizes.clear();
    }

    @Override
    public Size getPictureSize(int cameraType) {
        Size size = outPictureSizes.get(cameraType);
        if (size != null) {
            return size;
        }
        size = CameraHelper.getSizeWithClosestRatioSizeAndQuality(
                pictureSizes, expectAspectRatio, expectSize, mediaQuality);
        outPictureSizes.put(cameraType, size);
//        LogManager.iTag(TAG, "getPictureSize : " + size);
        return size;
    }

    @Override
    public Size getPicturePreviewSize(int cameraType) {
        Size size = outPicturePreviewSizes.get(cameraType);
        if (size != null) {
            return size;
        }
        size = CameraHelper.getSizeWithClosestRatio(previewSizes, getPictureSize(cameraType));
        outPicturePreviewSizes.put(cameraType, size);
        LogManager.iTag(TAG, "getPicturePreviewSize : " + size);
        return size;
    }

    @Override
    public Size getVideoSize(int cameraType) {
        Size size = outVideoSizes.get(cameraType);
        if (size != null) {
            return size;
        }
        size = CameraHelper.getSizeWithClosestRatioSizeAndQuality(
                videoSizes, expectAspectRatio, expectSize, mediaQuality);
        outVideoSizes.put(cameraType, size);
        LogManager.iTag(TAG, "getVideoSize : " + size);
        return null;
    }

    @Override
    public Size getVideoPreviewSize(int cameraType) {
        Size size = outVideoPreviewSizes.get(cameraType);
        if (size != null) {
            return size;
        }
        size = CameraHelper.getSizeWithClosestRatio(previewSizes, getVideoSize(cameraType));
        outVideoPreviewSizes.put(cameraType, size);
        LogManager.iTag(TAG, "getVideoPreviewSize : " + size);
        return size;
    }
}
