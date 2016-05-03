package com.bitdubai.android_core.app.common.version_1.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by mati on 2016.03.07..
 */
public class MainLayoutHelper {

    //color 0 is not color
    public static void setTranslucentStatusBar(Window window,int color) {
        if (window == null) return;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatusBarLollipop(window,color);
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBarKiKat(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatusBarLollipop(Window window,int color) {
        if(color!=0) window.setStatusBarColor(color);
//                window.getContext()
//                        .getResources()
//                        .getColor(R.color. / add here your translucent color code /));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatusBarKiKat(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


}
