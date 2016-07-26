package com.bitdubai.fermat_android_api.ui.util;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;


/**
 * Created by Matias Furszyfer
 */
public class BitmapWorkerTaskWithRes extends AsyncTask<Integer, Void, Drawable> {

    private final WeakReference<ImageView> imageViewReference;
    private final Resources res;
    private final int resImageInCaseOfError;
    private boolean isCircle = false;

    public BitmapWorkerTaskWithRes(ImageView imageView, Resources res, @Nullable int resImageInCaseOfError, boolean isCircle) {
        this.res = res;
        this.isCircle = isCircle;
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.resImageInCaseOfError = resImageInCaseOfError;
    }

    // Decode image in background.
    @Override
    protected Drawable doInBackground(Integer... params) {
        int data = params[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return res.getDrawable(data, null);
        } else {
            return res.getDrawable(data);
        }
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Drawable bitmap) {
        final ImageView imageView = imageViewReference.get();
        if (bitmap != null) {
            //if (imageView != null) {
            //imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(res,bitmap));
            if (imageView != null) {
                imageView.setImageDrawable((isCircle) ? ImagesUtils.getRoundedBitmap(res, ((BitmapDrawable) bitmap).getBitmap()) : bitmap);
            }
            //}
        } else {
            if (isCircle)
                Picasso.with(imageView.getContext()).load(resImageInCaseOfError).transform(new CircleTransform()).into(imageView);
            else
                Picasso.with(imageView.getContext()).load(resImageInCaseOfError).into(imageView);
        }
    }


}