package com.bitdubai.android_core.app.common.version_1.util.res_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.drawables.Badge;

import static com.bitdubai.fermat_android_api.core.FrameworkAvailableDrawables.IC_ARROW_BACK_WHITE;
import static com.bitdubai.fermat_android_api.core.FrameworkAvailableDrawables.IC_BADGE;

/**
 * Created by Matias Furszyfer on 2016.06.19..
 */
public class ResDrawableFrameworkHelper {


    public static <I extends FermatDrawable> Drawable obtainDrawable(Context context, I fermatDrawable) {
        Drawable drawable = null;
        switch (fermatDrawable.getId()) {
            case IC_ARROW_BACK_WHITE:
                drawable = DrawableUtils.resToDrawable(context, R.drawable.arrow_left);
                break;
            case IC_BADGE:
                Badge badge = (Badge) fermatDrawable;
                drawable = new BadgeDrawable.BadgeDrawableBuilder(context).setCount(badge.getNumber()).setPosition(BadgeDrawable.Position.TOP_RIGHT).setTextSize(badge.getTestSize()).build();
                break;
        }
        return drawable;
    }

    public static int obtainResDrawable(Context context, int id) {
        int resId = 0;
        switch (id) {
            case IC_ARROW_BACK_WHITE:
                //todo: ver esto
                resId = R.drawable.arrow_left;
                break;
        }
        return resId;
    }


}
