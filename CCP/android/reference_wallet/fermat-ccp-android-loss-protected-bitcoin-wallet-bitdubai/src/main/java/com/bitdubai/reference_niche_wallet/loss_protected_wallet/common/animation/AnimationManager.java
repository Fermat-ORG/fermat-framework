package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;

/**
 * Created by mati on 2015.11.23
 */

public class AnimationManager implements ElementsWithAnimation {

    View rootView;
    View emptyListViewsContainer;
    private int[] emptyOriginalPos;


    public AnimationManager(View rootView, View emptyListViewsContainer) {
        this.rootView = rootView;
        this.emptyListViewsContainer = emptyListViewsContainer;
        this.emptyOriginalPos = new int[2];
    }

    @Override
    public void startCollapseAnimation(int verticalOffset) {
        moveViewToScreenCenter(emptyListViewsContainer);
    }

    @Override
    public void startExpandAnimation(int verticalOffSet) {
        moveViewToOriginalPosition(emptyListViewsContainer);
    }

    @Override
    public void startCollapseAnimation(Context context, int verticalOffset) {
        // no-op
    }

    @Override
    public void startExpandAnimation(Context context, int verticalOffset) {
        // no-op
    }

    private void moveViewToOriginalPosition(View view) {
        if(Build.VERSION.SDK_INT>17) {
            if(view!=null) {
                int position[] = new int[2];
                view.getLocationOnScreen(position);
                float centreY = rootView.getY() + rootView.getHeight() / 2;
                TranslateAnimation anim = new TranslateAnimation(emptyOriginalPos[0], 0, centreY - 250, 0);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                view.startAnimation(anim);
            }
        }
    }

    private void moveViewToScreenCenter( View view ) {
        if (Build.VERSION.SDK_INT > 17) {
            if(view!=null) {
                DisplayMetrics dm = new DisplayMetrics();
                rootView.getDisplay().getMetrics(dm);
                float centreY = rootView.getY() + rootView.getHeight() / 2;
                TranslateAnimation anim = new TranslateAnimation(0, emptyOriginalPos[0], 0, centreY - 250);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                view.startAnimation(anim);
            }
        }
    }


    public void setEmptyOriginalPos(int[] emptyOriginalPos) {
        this.emptyOriginalPos = emptyOriginalPos;
    }
}
