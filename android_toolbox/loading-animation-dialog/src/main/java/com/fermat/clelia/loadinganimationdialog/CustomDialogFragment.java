package com.fermat.clelia.loadinganimationdialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by Clelia López on 4/14/16
 */
public class CustomDialogFragment
        extends android.support.v4.app.DialogFragment {

    /**
     * Attributes
     */

    private static Context context;
    private static CustomDialogFragment loadingAnimationDialog;
    private static Bitmap background;
    private AnimationView animationView;


    public static CustomDialogFragment newInstance(Context parentContext, Bitmap bitmap) {
        context = parentContext;
        background = bitmap;

        loadingAnimationDialog = new CustomDialogFragment();
        loadingAnimationDialog.setCancelable(false);
        loadingAnimationDialog.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_NoTitleBar_Fullscreen);

        return loadingAnimationDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout containerLinear = (LinearLayout) inflater.inflate(R.layout.loading_animation_dialog, container, false);
        animationView = (AnimationView) containerLinear.getChildAt(0);

        getDialog().getWindow().setBackgroundDrawable(new BitmapDrawable(context.getResources(), background));

        return inflater.inflate(R.layout.loading_animation_dialog, container, false);
    }

    public AnimationView getAnimationView() {
        return animationView;
    }
}

