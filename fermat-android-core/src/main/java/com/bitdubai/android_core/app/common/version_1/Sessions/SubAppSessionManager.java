package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager{

    private Map<SubApps,SubAppsSession> lstSubAppSession;



    public SubAppSessionManager(){
        lstSubAppSession= new HashMap<>();
    }


    @Override
    public Map<SubApps,SubAppsSession> listOpenSubApps() {
        return lstSubAppSession;
    }

    @Override
    public SubAppsSession openSubAppSession(SubApps subApps, ErrorManager errorManager, WalletFactoryManager walletFactoryManager, ToolManager toolManager,WalletStoreModuleManager walletStoreModuleManager,WalletPublisherModuleManager walletPublisherManager,IntraUserModuleManager intraUserModuleManager,AssetFactoryModuleManager assetFactoryModuleManager) {

        switch (subApps){
            case CWP_WALLET_FACTORY:
                WalletFactorySubAppSession subAppSession = new WalletFactorySubAppSession(subApps,errorManager,walletFactoryManager);
                lstSubAppSession.put(subApps, subAppSession);
                return subAppSession;
            case CWP_WALLET_STORE:
                WalletStoreSubAppSession walletStoreSubAppSession = new WalletStoreSubAppSession(subApps,errorManager,walletStoreModuleManager);
                lstSubAppSession.put(subApps,walletStoreSubAppSession);
                return walletStoreSubAppSession;
            case CWP_DEVELOPER_APP:
                DeveloperSubAppSession developerSubAppSession = new DeveloperSubAppSession(subApps,errorManager,toolManager);
                lstSubAppSession.put(subApps, developerSubAppSession);
                return developerSubAppSession;
            case CWP_WALLET_MANAGER:
                break;
            case CWP_WALLET_PUBLISHER:
                WalletPublisherSubAppSession walletPublisherSubAppSession = new WalletPublisherSubAppSession(subApps,errorManager, walletPublisherManager);
                lstSubAppSession.put(subApps,walletPublisherSubAppSession);
                return walletPublisherSubAppSession;
            case CWP_WALLET_RUNTIME:
                break;
            case CWP_INTRA_USER:
                IntraUserSubAppSession intraUserSubAppSession = new IntraUserSubAppSession(subApps,errorManager,intraUserModuleManager);
                lstSubAppSession.put(subApps,intraUserSubAppSession);
                return intraUserSubAppSession;
            case DAP_ASSETS_FACTORY:
                AssetFactorySession assetFactorySession = new AssetFactorySession(subApps,errorManager,assetFactoryModuleManager);
                lstSubAppSession.put(subApps,assetFactorySession);
                return assetFactorySession;
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
    public SubAppsSession getSubAppsSession(SubApps subAppType) {
        return this.lstSubAppSession.get(subAppType);
    }


}
