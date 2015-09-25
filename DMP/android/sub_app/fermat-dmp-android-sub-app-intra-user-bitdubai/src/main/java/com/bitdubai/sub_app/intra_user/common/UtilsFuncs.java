package com.bitdubai.sub_app.intra_user.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_store.enums.InstallationStatus;

import static com.bitdubai.fermat_api.layer.ccp_middleware.wallet_store.enums.InstallationStatus.INSTALLED;
import static com.bitdubai.fermat_api.layer.ccp_middleware.wallet_store.enums.InstallationStatus.NOT_UNINSTALLED;
import static com.bitdubai.fermat_api.layer.ccp_middleware.wallet_store.enums.InstallationStatus.UPGRADE_AVAILABLE;
import com.intra_user.bitdubai.R;
/**
 * Created by Matias Furszyfer on 28/08/15.
 */
public class UtilsFuncs {

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

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
}
