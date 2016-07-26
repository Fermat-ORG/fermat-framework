package com.bitdubai.fermat_android_api.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by Matias Furszyfer on 2016.07.02..
 */
public class DrawableUtils {

    public static Drawable resToDrawable(Context context, int resId) {
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resId);
        } else {
            drawable = ContextCompat.getDrawable(context, resId);
        }
        return drawable;
    }
}
