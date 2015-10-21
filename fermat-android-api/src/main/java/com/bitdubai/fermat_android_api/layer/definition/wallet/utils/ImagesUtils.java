package com.bitdubai.fermat_android_api.layer.definition.wallet.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Matias Furszyfer on 2015.10.02..
 */
public class ImagesUtils {

    /**
     * Return a rounded bitmap version of the image contained in the drawable resource
     *
     * @param res        the context resources
     * @param drawableId the id of the drawable resource
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, int drawableId) {
        Bitmap srcBitmap = BitmapFactory.decodeResource(res, drawableId);
        return getRoundedBitmap(res, srcBitmap);
    }

    /**
     * Return a rounded bitmap version of the given bitmap
     *
     * @param res       the context resources
     * @param srcBitmap the bitmap with the image
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, Bitmap srcBitmap) {
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(res, srcBitmap);

        int radius = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        bitmapDrawable.setCornerRadius(radius);
        bitmapDrawable.setAntiAlias(true);

        return bitmapDrawable;
    }

    /**
     * Return a rounded bitmap version of the image contained in the byte array
     *
     * @param res        the context resources
     * @param imgInBytes the byte array with the image
     * @return the RoundedBitmapDrawable with the rounded bitmap
     */
    public static RoundedBitmapDrawable getRoundedBitmap(Resources res, byte[] imgInBytes) {
        Bitmap srcBitmap = BitmapFactory.decodeByteArray(imgInBytes, 0, imgInBytes.length);
        return getRoundedBitmap(res, srcBitmap);
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
