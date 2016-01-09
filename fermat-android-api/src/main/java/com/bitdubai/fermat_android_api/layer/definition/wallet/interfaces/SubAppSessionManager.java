package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface SubAppSessionManager {


    public FermatSession<InstalledSubApp,?> openSubAppSession(InstalledSubApp subApp,ErrorManager errorManager,ModuleManager moduleManager,AppConnections appConnections);

    public boolean closeSubAppSession(String subAppPublicKey);
    //subApp publicKey
    public Map<String, FermatSession<InstalledSubApp,?>> listOpenSubApps();

    public boolean isSubAppOpen(String subAppPublicKey);
    public FermatSession<InstalledSubApp,?> getSubAppsSession(String subAppPublicKey);

}
