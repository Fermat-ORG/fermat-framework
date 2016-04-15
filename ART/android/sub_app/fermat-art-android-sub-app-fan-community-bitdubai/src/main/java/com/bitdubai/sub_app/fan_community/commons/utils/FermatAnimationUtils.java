package com.bitdubai.sub_app.fan_community.commons.utils;

import android.view.View;
import android.view.animation.AnimationUtils;

import com.bitdubai.sub_app.fan_community.R;

import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class FermatAnimationUtils {

    /**
     * Show or Hide any view
     *
     * @param show true if you want to show the view, otherwise false
     * @param view View object to show or hide
     */
    public static void showView(boolean show, View view) {
        if (view == null)
            return;
        view.setAnimation(AnimationUtils
                .loadAnimation(view.getContext(), show ? R.anim.abc_fade_in : R.anim.abc_fade_out));
        if (show && (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE)) {
            view.setVisibility(View.VISIBLE);
        } else if (!show && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Show or hide empty view if needed
     */
    public static void showEmpty(boolean isAttached, View empty, List data) {
        if (!isAttached || empty == null)
            return;
        if (data == null || data.isEmpty()) {
            if (empty.getVisibility() == View.GONE || empty.getVisibility() == View.INVISIBLE) {
                empty.setAnimation(AnimationUtils.loadAnimation(empty.getContext(), R.anim.abc_fade_in));
                empty.setVisibility(View.VISIBLE);
            }
        } else if (empty.getVisibility() == View.VISIBLE) {
            empty.setAnimation(AnimationUtils.loadAnimation(empty.getContext(), R.anim.abc_fade_out));
            empty.setVisibility(View.GONE);
        }
    }
}
