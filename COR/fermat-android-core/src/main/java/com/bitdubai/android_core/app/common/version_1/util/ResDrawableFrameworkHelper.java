package com.bitdubai.android_core.app.common.version_1.util;

import android.content.Context;

import com.bitdubai.fermat.R;

import static com.bitdubai.android_core.app.common.version_1.util.FrameworkAvailableDrawables.IC_ARROW_BACK_WHITE;

/**
 * Created by Matias Furszyfer on 2016.06.19..
 */
public class ResDrawableFrameworkHelper {

    public static int obtainFrameworkAvailableResDrawable(Context context,int id){
        int resId = 0;
        switch (id){
            case IC_ARROW_BACK_WHITE:
                //todo: ver esto
                resId = R.drawable.arrow_left;
                break;
        }
        return resId;
    }
}
