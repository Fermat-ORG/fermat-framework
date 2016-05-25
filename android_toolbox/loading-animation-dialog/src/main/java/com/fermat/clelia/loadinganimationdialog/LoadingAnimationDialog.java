package com.fermat.clelia.loadinganimationdialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fermat.clelia.loadinganimationdialog.Utility.AnimationBuilder;
import com.fermat.clelia.loadinganimationdialog.Utility.BlurBuilder;
import com.fermat.clelia.loadinganimationdialog.Utility.ScreenUnitConverter;

/**
 * Created by Clelia López on 4/22/16
 */
public class LoadingAnimationDialog {

    /**
     * Attributes
     */
    private Context context;
    private CustomDialogFragment dialog;


    public LoadingAnimationDialog(Context mContext) {
        this.context = mContext;

        initialize();
    }

    private void initialize() {
        View rootView = ((AppCompatActivity)context).getWindow().getDecorView();
        Bitmap blurredBitmap = BlurBuilder.getScreenshot(rootView, context);
        int radios = 3;
        if(ScreenUnitConverter.getDensityIndex(context) > 2)
            radios = 4;

        blurredBitmap = BlurBuilder.fastblur(blurredBitmap, radios);
        dialog = CustomDialogFragment.newInstance(context, blurredBitmap);
    }

    public void showDialog() {
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        dialog.show(fragmentManager, "LoadingAnimationDialog");
    }

    public void dismissDialog() {
        for (AnimationBuilder animation : dialog.getAnimationView().getAnimations())
            animation.stop();
        dialog.dismiss();
    }
}

