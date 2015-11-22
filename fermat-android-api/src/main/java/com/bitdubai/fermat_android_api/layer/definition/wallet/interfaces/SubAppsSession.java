package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppsSession extends FermatSession{

    public InstalledSubApp getSubAppSessionType();

    //public ToolManager getToolManager();
    //public WalletFactoryProjectManager getWalletFactoryManager();

}
