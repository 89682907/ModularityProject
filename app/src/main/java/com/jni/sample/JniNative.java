package com.jni.sample;

public class JniNative {
    static {
        System.loadLibrary("jni-lib");
    }

    public static native int JniCAdd(int a, int b);

    public static native int JniCSub(int a, int b);
}
