package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager{

    private Map<String,SubAppsSession> lstSubAppSession;



    public SubAppSessionManager(){
        lstSubAppSession= new HashMap<String,SubAppsSession>();
    }


    @Override
    public Map<String,SubAppsSession> listOpenSubApps() {
        return lstSubAppSession;
    }

    @Override
    public SubAppsSession openSubAppSession(SubApps subApps, ErrorManager errorManager, WalletFactoryManager walletFactoryManager, ToolManager toolManager,WalletStoreModuleManager walletStoreModuleManager,WalletPublisherModuleManager walletPublisherManager) {

        switch (subApps){
            case CWP_WALLET_FACTORY:
                WalletFactorySubAppSession subAppSession = new WalletFactorySubAppSession(subApps,errorManager,walletFactoryManager);
                lstSubAppSession.put(subApps.name(), subAppSession);
                return subAppSession;
            case CWP_WALLET_STORE:
                WalletStoreSubAppSession walletStoreSubAppSession = new WalletStoreSubAppSession(subApps,errorManager,walletStoreModuleManager);
                lstSubAppSession.put(subApps.getCode(),walletStoreSubAppSession);
                return walletStoreSubAppSession;
            case CWP_DEVELOPER_APP:
                DeveloperSubAppSession developerSubAppSession = new DeveloperSubAppSession(subApps,errorManager,toolManager);
                lstSubAppSession.put(subApps.getCode(), developerSubAppSession);
                return developerSubAppSession;
            case CWP_WALLET_MANAGER:
                break;
            case CWP_WALLET_PUBLISHER:
                WalletPublisherSubAppSession walletPublisherSubAppSession = new WalletPublisherSubAppSession(subApps,errorManager, walletPublisherManager);
                lstSubAppSession.put(subApps.getCode(),walletPublisherSubAppSession);
                return walletPublisherSubAppSession;
            case CWP_WALLET_RUNTIME:
                break;
            default:
                return null;
                //throw new FermatException("")
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

    @Override
    public SubAppsSession getSubAppsSession(String subAppType) {
        return this.lstSubAppSession.get(subAppType);
    }


}
