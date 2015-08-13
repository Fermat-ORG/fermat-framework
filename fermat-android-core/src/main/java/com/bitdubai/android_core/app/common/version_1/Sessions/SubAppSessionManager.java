package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProjectManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager{

    private Map<SubApps,SubAppsSession> lstSubAppSession;



    public SubAppSessionManager(){
        lstSubAppSession= new HashMap<SubApps,SubAppsSession>();
    }


    @Override
    public Map<SubApps,SubAppsSession> listOpenSubApps() {
        return lstSubAppSession;
    }

    @Override
    public SubAppsSession openSubAppSession(SubApps subApps, ErrorManager errorManager, Plugin plugin) {

        switch (subApps){
            case CWP_WALLET_FACTORY:
                WalletFactorySubAppSession subAppSession = new WalletFactorySubAppSession(subApps,errorManager,(WalletDescriptorFactoryProjectManager)plugin);
                lstSubAppSession.put(subApps, subAppSession);
                return subAppSession;
            case CWP_WALLET_STORE:
                break;
            case CWP_DEVELOPER_APP:
                DeveloperSubAppSession developerSubAppSession = new DeveloperSubAppSession(subApps,errorManager,(ToolManager)plugin);
                lstSubAppSession.put(subApps, developerSubAppSession);
                return developerSubAppSession;
            case CWP_WALLET_MANAGER:
                break;
            case CWP_WALLET_PUBLISHER:
                break;
            case CWP_WALLET_RUNTIME:
                break;
        }


        return null;

    }


    @Override
    public boolean closeSubAppSession(SubApps subApps) {
        try {
            lstSubAppSession.remove(new DeveloperSubAppSession(subApps));
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public boolean isSubAppOpen(SubApps subApps) {
        return lstSubAppSession.containsKey(subApps);
    }


}
