package com.bitdubai.sub_app.crypto_broker_community.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author furszy, lnacosta
 * @version 1.0.0
 */
public class UtilFunctions {

    public static Bitmap getRoundedShape(final Bitmap scaleBitmapImage) {

        int targetWidth = 50;
        int targetHeight = 50;

        Bitmap targetBitmap = Bitmap.createBitmap(
                targetWidth,
                targetHeight,
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(targetBitmap);

        Path path = new Path();

        path.addCircle(
                ((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW
        );

        canvas.clipPath(path);

        canvas.drawBitmap(
                scaleBitmapImage,
                new Rect(0, 0, scaleBitmapImage.getWidth(), scaleBitmapImage.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight),
                null
        );

        return targetBitmap;
    }
}
