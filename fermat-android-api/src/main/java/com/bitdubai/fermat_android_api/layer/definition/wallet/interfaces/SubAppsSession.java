package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.ccp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppsSession {

    public SubApps getSubAppSessionType();
    public void setData (String key,Object object);
    public Object getData (String key);
    public ErrorManager getErrorManager();
    //public ToolManager getToolManager();
    //public WalletFactoryProjectManager getWalletFactoryManager();
}
