package com.bitdubai.fermat_android_api.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;



/**
 * Created by Matias Furszyfer on 2015.12.02..
 */
public class FermatAnimationsUtils {

    public static void showEmpty(Activity activity,boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(activity,
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show &&
                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.GONE);
        }
    }
}
