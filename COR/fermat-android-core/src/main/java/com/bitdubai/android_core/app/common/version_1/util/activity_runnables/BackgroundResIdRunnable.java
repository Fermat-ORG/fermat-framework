package com.bitdubai.android_core.app.common.version_1.util.activity_runnables;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 27/08/16.
 */
public class BackgroundResIdRunnable implements Runnable {

    private int resId;
    private WeakReference<View> view;

    @Override
    public void run() {
        view.get().setBackgroundResource(resId);
    }

    public void loadBeforeRun(int resId,View view){
        this.resId = resId;
        this.view = new WeakReference<View>(view);
    }

    public void clear(){
        view.clear();
    }
}
