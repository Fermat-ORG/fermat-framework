package com.bitdubai.fermat_android_api.utils;

import android.content.Context;

/**
 * Created by Matias Furszyfer on 2016.02.24..
 */
public class FermatScreenCalculator {


    public static int getPx(Context context, float dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }


}
