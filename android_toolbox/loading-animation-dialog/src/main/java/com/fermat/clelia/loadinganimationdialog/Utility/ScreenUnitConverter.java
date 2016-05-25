package com.fermat.clelia.loadinganimationdialog.Utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Clelia López on 3/8/16
 */
public class ScreenUnitConverter {

    public enum Unit {
        PIXELS, DP
    }

    /**
     * @param value - if unit PIXEL is passed in, a pixel equivalent value is return
     *              - if unit DP is passed in, a dp equivalent value is return
     * @return - equivalent number in pixels or dp
     */
    public static float convertValueTo(Context context, int value, Unit unit) {
        float result = 0;
        switch (unit) {
            case DP:
                float localDensity = context.getResources().getDisplayMetrics().density;
                result = value / localDensity;
                break;
            case PIXELS:
                result = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
                break;
        }
        return result;
    }

    /**
     * @return - width resolution in pixels or dp
     */
    public static float getScreenWidth(Context context, Unit unit) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float result = 0;
        switch (unit) {
            case PIXELS:
                result = displayMetrics.widthPixels;
                break;
            case DP:
                result = displayMetrics.widthPixels / displayMetrics.density;
                break;
        }
        return result;
    }

    /**
     * @return - height resolution in pixels or dp
     */
    public static float getScreenHeight(Context context, Unit unit) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float result = 0;
        switch (unit) {
            case PIXELS:
                result = displayMetrics.heightPixels;
                break;
            case DP:
                result = displayMetrics.heightPixels / displayMetrics.density;
                break;
        }
        return result;
    }

    public static float getDensityIndex(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
