package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppsSession extends FermatSession{

    public SubApps getSubAppSessionType();

    //public ToolManager getToolManager();
    //public WalletFactoryProjectManager getWalletFactoryManager();

}
