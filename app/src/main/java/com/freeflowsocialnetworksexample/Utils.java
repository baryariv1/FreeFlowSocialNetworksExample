package com.freeflowsocialnetworksexample;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by byariv on 1/11/2016.
 */
public class Utils {

    /**
     * Convert dp to px
     *
     * @param dp - to convert
     * @return - dp in px
     */
    public static int dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
