package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by nelson on 12/10/15.
 */
public class UtilsFuncs {

    public static Bitmap getRoundedShape(Bitmap srcBitmap) {
        int targetWidth = 50, targetHeight = 50;

        Bitmap targetBitmap = null;
        if (srcBitmap != null) {

            targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

            float x = ((float) targetWidth - 1) / 2;
            float y = ((float) targetHeight - 1) / 2;
            float radius = Math.min((float) targetWidth, (float) targetHeight) / 2;

            Path path = new Path();
            path.addCircle(x, y, radius, Path.Direction.CCW);

            Rect src = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
            Rect dst = new Rect(0, 0, targetWidth, targetHeight);

            Canvas canvas = new Canvas(targetBitmap);
            canvas.clipPath(path);
            canvas.drawBitmap(srcBitmap, src, dst, null);
        }

        return targetBitmap;
    }
}
