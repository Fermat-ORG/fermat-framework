package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import java.math.BigDecimal;

/**
 * Chat List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 09/01/16
 * @version 1.0
 */

/**
 * This class contains static utility methods.
 */
public class Utils {

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static String avoidingScientificNot(String str) {
        if (isNumeric(str)) {
            str = new BigDecimal(str).toPlainString();
        }
        return str;

    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
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
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
}
