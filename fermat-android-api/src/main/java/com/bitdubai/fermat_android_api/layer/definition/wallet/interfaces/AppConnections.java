package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public abstract class AppConnections<S extends FermatSession> implements FermatAppConnection{

    private WeakReference<Context> activity;
    private S fullyLoadedSession;

    public AppConnections(Context activity) {
        this.activity = new WeakReference<>(activity);
    }

    public abstract PluginVersionReference[] getPluginVersionReference();

    public Context getContext() {
        return activity.get();
    }


    public void setFullyLoadedSession(S session) {
        this.fullyLoadedSession = session;
    }

    public S getFullyLoadedSession(){
        return fullyLoadedSession;
    }



    public void setActivity(WeakReference<Context> activity) {
        this.activity = activity;
    }


    public NotificationPainter getNotificationPainter(String code){
        return null;
    }


    protected  FermatSession getSession(){return null;};


    public void changeApp(String appPublicKey) throws Exception {
        getApplicationManager().openFermatApp(appPublicKey);
    }

    public void goHome(){
        getApplicationManager().openFermatHome();
    }

    public FermatApplicationCaller getApplicationManager(){
        return ((FermatApplicationSession)(activity.get()).getApplicationContext()).getApplicationManager();
    }

    public int getResource(String id) {
        return 0;
    }
}
