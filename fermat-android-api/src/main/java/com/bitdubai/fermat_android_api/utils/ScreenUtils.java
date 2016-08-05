package com.bitdubai.fermat_android_api.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by mati on 05/08/16.
 */
public class ScreenUtils {

    public enum ScreenSize {
        LARGE, NORMAL, UNDEFINED, SMALL
    }

    public static ScreenSize getScreenSize(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        ScreenSize screenSizeType = null;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenSizeType = ScreenSize.LARGE;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenSizeType = ScreenSize.NORMAL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenSizeType = ScreenSize.SMALL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                screenSizeType = ScreenSize.UNDEFINED;
                break;
            default:
                screenSizeType = ScreenSize.UNDEFINED;
        }
        return screenSizeType;
    }

}
