package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface SubAppSessionManager {


    public SubAppsSession openSubAppSession(InstalledSubApp subApp,String subAppPublicKey, ErrorManager errorManager,ModuleManager moduleManager);

    public boolean closeSubAppSession(String subAppPublicKey);
    //subApp publicKey
    public Map<String,SubAppsSession> listOpenSubApps();

    public boolean isSubAppOpen(String subAppPublicKey);
    public SubAppsSession getSubAppsSession(String subAppPublicKey);

}
