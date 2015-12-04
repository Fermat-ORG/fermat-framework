package com.bitdubai.sub_app.intra_user_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserSubAppSession extends AbstractFermatSession<InstalledSubApp,IntraUserModuleManager,SubAppResourcesProviderManager> implements SubAppsSession{

    public static final String BASIC_DATA = "catalog item";
    public static final String PREVIEW_IMGS = "preview images";
    public static final String DEVELOPER_NAME = "developer name";


    /**
     * Create a session for the Wallet Store SubApp
     *

     * @param errorManager             the error manager
     * @param intraUserModuleManager the module of this SubApp
     */
    public IntraUserSubAppSession(InstalledSubApp subApp, ErrorManager errorManager, IntraUserModuleManager intraUserModuleManager) {
        super(subApp.getAppPublicKey(),subApp,errorManager,intraUserModuleManager,null);
    }

    /**
     * Return the SubApp type
     *
     * @return SubApps instance indicating the type
     */
    @Override
    public InstalledSubApp getSubAppSessionType() {
        return getFermatApp();
    }


}
