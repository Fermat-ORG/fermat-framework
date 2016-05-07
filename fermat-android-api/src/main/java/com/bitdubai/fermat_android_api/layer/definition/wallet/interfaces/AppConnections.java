package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import android.content.Context;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public abstract class AppConnections<S extends FermatSession> implements FermatAppConnection{

    WeakReference<Context> activity;
    ActiveActorIdentityInformation activeIdentity;
    S fullyLoadedSession;

    public AppConnections(Context activity) {
        this.activity = new WeakReference<>(activity);
        activeIdentity = null;
    }

    public abstract PluginVersionReference getPluginVersionReference();

    public FermatSession buildSession(FermatApp fermatApp,ModuleManager manager,ErrorManager errorManager){
        AbstractFermatSession session = getSession();
        session.setErrorManager(errorManager);
        session.setModuleManager(manager);
        session.setFermatApp(fermatApp);
        session.setPublicKey(fermatApp.getAppPublicKey());
        return session;
    }

    protected abstract AbstractFermatSession getSession();

    public Context getContext() {
        return activity.get();
    }

    public final void setActiveIdentity(ActiveActorIdentityInformation activeIdentity){
        this.activeIdentity = activeIdentity;
    }

    public ActiveActorIdentityInformation getActiveIdentity() {
        return activeIdentity;
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
}
