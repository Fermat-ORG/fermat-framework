package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public abstract class AppConnections implements FermatAppConnection{

    WeakReference<Activity> activity;

    public AppConnections(Activity activity) {
        this.activity = new WeakReference<Activity>(activity);
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

    public Activity getActivity() {
        return activity.get();
    }
}
