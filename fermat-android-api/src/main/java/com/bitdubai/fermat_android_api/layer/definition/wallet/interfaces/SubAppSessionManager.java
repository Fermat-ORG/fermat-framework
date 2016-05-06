package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface SubAppSessionManager {


    FermatSession<InstalledSubApp,?> openSubAppSession(InstalledSubApp subApp, ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections);

    boolean closeSubAppSession(String subAppPublicKey);
    //subApp publicKey
    Map<String, FermatSession<InstalledSubApp,?>> listOpenSubApps();

    boolean isSubAppOpen(String subAppPublicKey);
    FermatSession<InstalledSubApp,?> getSubAppsSession(String subAppPublicKey);

}
