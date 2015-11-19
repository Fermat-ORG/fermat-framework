package com.bitdubai.sub_app.intra_user_community.common.utils;

import android.view.View;
import android.view.animation.AnimationUtils;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.List;

/**
 * Created by mati on 2015.10.27..
 */
public class FernatAnimationUtils {

    /**
     * Show or Hide any view
     *
     * @param show true if you want to show the view, otherwise false
     * @param view View object to show or hide
     */
    public static void showView(boolean show, View view) {
        if (view == null)
            return;
        view.setAnimation(android.view.animation.AnimationUtils
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
