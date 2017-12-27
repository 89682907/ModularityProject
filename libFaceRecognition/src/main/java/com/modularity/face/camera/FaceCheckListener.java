package com.modularity.face.camera;

/**
 * 人脸识别保存的图片人脸检测监听
 * Created by jishen on 2017/7/19.
 */

public interface FaceCheckListener {
    void recognition(boolean detected, String filepath);
}
