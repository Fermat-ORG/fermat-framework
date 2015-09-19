package com.bitdubai.sub_app.wallet_factory.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Utilities
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public final class Utils {


    /**
     * set visibility to any view object
     *
     * @param context Context
     * @param show    true if you want to show the view, otherwise false
     * @param view    View to show or hide
     * @param animate true if you want to apply fade in/out animation, otherwise false
     */
    public static void setVisibility(Context context, View view, boolean show, boolean animate) {
        if (view == null)
            return;
        Animation anim = null;
        if (animate) {
            anim = AnimationUtils.loadAnimation(context, show ? android.R.anim.fade_in :
                    android.R.anim.fade_out);
            view.setAnimation(anim);
        }
        if (show && (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE))
            view.setVisibility(View.VISIBLE);
    }

}
