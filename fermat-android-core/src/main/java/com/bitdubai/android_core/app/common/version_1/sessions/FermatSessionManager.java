package com.bitdubai.android_core.app.common.version_1.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.02.22..
 */
public class FermatSessionManager {

    private Map<String, FermatSession<FermatApp,?>> lstAppSession;


    public FermatSessionManager() {
        lstAppSession = new HashMap<>();
    }


    public Map<String, FermatSession<FermatApp,?>> listOpenApps() {
        return lstAppSession;
    }

    public FermatSession<FermatApp,?> openAppSession(FermatApp app, ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections) {
        FermatSession AppsSession  = appConnections.buildSession(app, moduleManager, errorManager);
        lstAppSession.put(app.getAppPublicKey(), AppsSession);
        return AppsSession;
    }


    public boolean closeAppSession(String subAppPublicKey) {
        try {
            lstAppSession.remove(subAppPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    public FermatSession<FermatApp,?> getAppsSession(String appPublicKey) {
        if(appPublicKey == null) throw new NullPointerException("Publick key de la app se encuentra en null");
        return lstAppSession.get(appPublicKey);
    }

    public boolean isSessionOpen(String appPublicKey) {
        return lstAppSession.containsKey(appPublicKey);
    }
}
