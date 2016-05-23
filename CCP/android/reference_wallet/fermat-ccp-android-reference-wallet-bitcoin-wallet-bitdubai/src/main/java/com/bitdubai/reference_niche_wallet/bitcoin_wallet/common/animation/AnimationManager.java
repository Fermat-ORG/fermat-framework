package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.NavigationBarTools;


/**
 * Created by mati on 2015.11.23
 *
 * Modify by Clelia López
 */
public class AnimationManager
        implements ElementsWithAnimation {

    private View rootView;
    private View emptyListViewsContainer;
    private int navigationBarHeight = 0;


    public AnimationManager(View rootView, View emptyListViewsContainer) {
        this.rootView = rootView;
        this.emptyListViewsContainer = emptyListViewsContainer;
    }

    @Override
    public void startCollapseAnimation(int verticalOffset) {
        // no-op
    }

    @Override
    public void startExpandAnimation(int verticalOffset) {
        // no-op
    }

    @Override
    public void startCollapseAnimation(Context context, int verticalOffset) {
        Point navigationBarSize = NavigationBarTools.getNavigationBarSize(context);
        if (navigationBarSize != null)
            navigationBarHeight = navigationBarSize.y;

        moveViewToScreenCenter(emptyListViewsContainer);
    }

    @Override
    public void startExpandAnimation(Context context, int verticalOffset) {
        moveViewToOriginalPosition(emptyListViewsContainer);
    }

    private void moveViewToOriginalPosition(View view) {
        ObjectAnimator animator;
        if(Build.VERSION.SDK_INT> 17 && view != null) {
            animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -getCentreY(), 0);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(700);
            animator.start();
        }
    }

    private void moveViewToScreenCenter(View view) {
        ObjectAnimator animator;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rootView.getDisplay().getMetrics(displayMetrics);
            float density = displayMetrics.density;
            if (Build.VERSION.SDK_INT > 17 && view != null) {
                animator = ObjectAnimator.ofFloat(view,  View.TRANSLATION_Y,  0, -getCentreY() - navigationBarHeight/density);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(700);
                animator.start();
            }
        }
    }

    public float getCentreY() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rootView.getDisplay().getMetrics(displayMetrics);
            float density = displayMetrics.density;
            float screenHeight = displayMetrics.heightPixels;
            float balanceHeight = density * 330;
            return (screenHeight - balanceHeight) / 2;
        }
        return 0;
    }
}
