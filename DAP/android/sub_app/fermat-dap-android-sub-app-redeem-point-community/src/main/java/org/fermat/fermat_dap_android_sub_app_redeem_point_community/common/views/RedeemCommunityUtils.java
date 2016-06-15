package org.fermat.fermat_dap_android_sub_app_redeem_point_community.common.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;

/**
 * Created by nerio on 3/6/2016.
 */
public class RedeemCommunityUtils {

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgeDrawable) {
//            badge = (BadgeDrawable) reuse;
//        } else {
//            badge = new BadgeDrawable(context);
//        }
//
//        badge.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static ShapeDrawable getShapeDrawable(int size, int color) {
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.setIntrinsicHeight(size);
        circle.setIntrinsicWidth(size);
        circle.getPaint().setColor(color);
        return circle;
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        //final Paint paintBorder = new Paint();
        // paintBorder.setColor(Color.GREEN);
        //paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
        //BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(output, canvas.getWidth(), canvas.getHeight(), false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //paint.setShader(shader);
        paint.setAntiAlias(true);
        //paintBorder.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        //int circleCenter = bitmap.getWidth() / 2;
        //int borderWidth = 2;
        //canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);
        //canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter - 4.0f, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //canvas.drawBitmap(bitmap, circleCenter + borderWidth, circleCenter + borderWidth, paint);
        bitmap.recycle();
        return output;
    }

    public static Bitmap decodeFile(Context context, int resId) {
// decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, o);
// Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 50;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale++;
        }
// decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeResource(context.getResources(), resId, o2);
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