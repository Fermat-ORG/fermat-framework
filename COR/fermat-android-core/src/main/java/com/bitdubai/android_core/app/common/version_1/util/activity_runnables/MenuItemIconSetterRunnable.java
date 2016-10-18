package com.bitdubai.android_core.app.common.version_1.util.activity_runnables;

import android.view.MenuItem;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 27/08/16.
 */
public class MenuItemIconSetterRunnable implements Runnable{

    private int iconRes = 0;
    private WeakReference<MenuItem> weakReference;

    @Override
    public void run() {
        try {
            weakReference.get().setIcon(iconRes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadBeforeRun(int iconRes,MenuItem menuItem){
        this.iconRes = iconRes;
        this.weakReference = new WeakReference<MenuItem>(menuItem);
    }


    public void clear() {
        weakReference.clear();
    }
}
