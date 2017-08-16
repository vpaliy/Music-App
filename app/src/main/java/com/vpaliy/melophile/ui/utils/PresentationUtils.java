package com.vpaliy.melophile.ui.utils;

import android.content.res.Resources;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

public class PresentationUtils {

    private PresentationUtils(){
        throw new UnsupportedOperationException();
    }

    public static int getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static @CheckResult @ColorInt int modifyAlpha(@ColorInt int color,
                                                         @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static @CheckResult
    @ColorInt int modifyAlpha(@ColorInt int color,
                              @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }

}
