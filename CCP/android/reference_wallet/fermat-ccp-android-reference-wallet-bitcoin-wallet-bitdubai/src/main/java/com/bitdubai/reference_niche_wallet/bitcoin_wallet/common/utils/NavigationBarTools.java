package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;

public class NavigationBarTools {

    public static Point getNavigationBarSize(Context context) {
        Point usableSize = getUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right
        if (usableSize.x < realScreenSize.x)
            return new Point(realScreenSize.x - usableSize.x, usableSize.y);

        // navigation bar at the bottom
        if (usableSize.y < realScreenSize.y)
            return new Point(usableSize.x, realScreenSize.y - usableSize.y);

        // navigation bar is not present
        return null;
    }

    private static Point getUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            //noinspection TryWithIdenticalCatches
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return size;
    }
}