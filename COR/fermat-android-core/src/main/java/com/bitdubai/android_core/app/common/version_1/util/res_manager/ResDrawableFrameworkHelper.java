package com.bitdubai.android_core.app.common.version_1.util.res_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bitdubai.fermat.R;

import static com.bitdubai.android_core.app.common.version_1.util.FrameworkAvailableDrawables.IC_ARROW_BACK_WHITE;

/**
 * Created by Matias Furszyfer on 2016.06.19..
 */
public class ResDrawableFrameworkHelper  {


    public static Drawable obtainDrawable(Context context, int id) {
        int resId = 0;
        switch (id){
            case IC_ARROW_BACK_WHITE:
                //todo: ver esto
                resId = R.drawable.arrow_left;
                break;
        }

        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resId);
        }else{
            drawable = ContextCompat.getDrawable(context,id);
        }
        return drawable;
    }

    public static int obtainResDrawable(Context context, int id) {
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
