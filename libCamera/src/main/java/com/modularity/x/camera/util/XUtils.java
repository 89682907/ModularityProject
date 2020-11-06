package com.modularity.x.camera.util;

import android.content.Context;

public final class XUtils {

    private XUtils() {
        throw new UnsupportedOperationException("U can't initialize me!");
    }

    public static int dp2Px(Context context, float dpValues) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValues * scale + 0.5f);
    }

    public static int sp2Px(Context context, float spValues) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValues * fontScale + 0.5f);
    }
}
