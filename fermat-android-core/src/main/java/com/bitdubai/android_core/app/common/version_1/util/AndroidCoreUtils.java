package com.bitdubai.android_core.app.common.version_1.util;

import android.content.Context;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.02..
 */
public class AndroidCoreUtils implements com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils {

    private BroadcasterInterface context;
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    private static final AndroidCoreUtils instance = new AndroidCoreUtils() ;

    public static AndroidCoreUtils getInstance(){
        return instance;
    }

    @Override
    public void publish(final BroadcasterType broadcasterType, final String code) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                context.publish(broadcasterType,code);
            }
        });
    }

    public BroadcasterInterface getContext() {
        return context;
    }

    public void setContextAndResume(BroadcasterInterface context) {
        this.context = context;
    }

    public void clear(){
        this.context = null;
    }

    public void stop(){
        this.context = null;
        executor.shutdownNow();
    }


}
