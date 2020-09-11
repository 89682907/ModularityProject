package com.modularity.common.utils.utilcode.util;

public final class TemperatureUtils {

    public static float cToF(float temp) {
        return (temp * 9) / 5 + 32;
    }

    public static float cToK(float temp) {
        return temp + 273.15f;
    }


    public static float fToC(float temp) {
        return (temp - 32) * 5 / 9;
    }

    public static float fToK(float temp) {
        return temp + 255.3722222222f;
    }


    public static float kToC(float temp) {
        return temp - 273.15f;
    }

    public static float kToF(float temp) {
        return temp - 459.67f;
    }
}
