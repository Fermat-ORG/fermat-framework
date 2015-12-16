package com.bitdubai.sub_app.intra_user_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserIdentitySubAppSession extends AbstractFermatSession<InstalledSubApp,IntraWalletUserIdentityManager,SubAppResourcesProviderManager> implements SubAppsSession {



    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Wallet Store Module
     */
    private IntraWalletUserIdentityManager moduleManager;


    /**
     * Create a session for the Wallet Store SubApp
     * @param errorManager             the error manager
     * @param moduleManager the module of this SubApp
     */
    public IntraUserIdentitySubAppSession(InstalledSubApp subApp, ErrorManager errorManager, IntraWalletUserIdentityManager moduleManager) {
        super(subApp.getAppPublicKey(),subApp,errorManager,moduleManager,null);
        this.errorManager = errorManager;
        this.moduleManager = moduleManager;
    }

    public IntraUserIdentitySubAppSession() {

    }



    /**
     * Return the Error Manager
     *
     * @return reference to the Error Manager
     */
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    /**
     * Return the Wallet Store Module
     *
     * @return reference to the Wallet Store Module
     */
    public IntraWalletUserIdentityManager getModuleManager() {
        return moduleManager;
    }

}
