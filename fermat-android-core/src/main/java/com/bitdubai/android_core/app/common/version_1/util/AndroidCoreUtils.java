package com.bitdubai.android_core.app.common.version_1.util;

import android.content.Context;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.02.02..
 */
public class AndroidCoreUtils implements com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils {

    private BroadcasterInterface context;

    private static final AndroidCoreUtils instance = new AndroidCoreUtils() ;

    public static AndroidCoreUtils getInstance(){
        return instance;
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code) {
        context.publish(broadcasterType,code);
    }

    public BroadcasterInterface getContext() {
        return context;
    }

    public void setContext(BroadcasterInterface context) {
        this.context = context;
    }

    public void clear(){
        this.context = null;
    }
}
