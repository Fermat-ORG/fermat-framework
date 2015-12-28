package com.bitdubai.android_core.app.common.version_1.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager {

    private Map<String, FermatSession<InstalledSubApp>> lstSubAppSession;


    public SubAppSessionManager() {
        lstSubAppSession = new HashMap<>();
    }


    @Override
    public Map<String, FermatSession<InstalledSubApp>> listOpenSubApps() {
        return lstSubAppSession;
    }

    @Override
    public FermatSession<InstalledSubApp> openSubAppSession(InstalledSubApp subApp, ErrorManager errorManager, ModuleManager moduleManager,AppConnections appConnections) {
        FermatSession<InstalledSubApp> subAppsSession  = appConnections.buildSession(subApp,moduleManager,errorManager);
        lstSubAppSession.put(subApp.getAppPublicKey(), subAppsSession);
        return subAppsSession;
    }


    @Override
    public boolean closeSubAppSession(String subAppPublicKey) {
        try {
            lstSubAppSession.remove(subAppPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public boolean isSubAppOpen(String subAppPublicKey) {
        return lstSubAppSession.containsKey(subAppPublicKey);
    }

    @Override
    public FermatSession<InstalledSubApp> getSubAppsSession(String subAppPublicKey) {
        return lstSubAppSession.get(subAppPublicKey);
    }


}
