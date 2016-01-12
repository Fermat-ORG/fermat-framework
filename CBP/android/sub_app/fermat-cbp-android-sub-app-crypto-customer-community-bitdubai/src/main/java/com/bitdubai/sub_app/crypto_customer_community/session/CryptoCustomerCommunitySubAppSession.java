package com.bitdubai.sub_app.crypto_customer_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class CryptoCustomerCommunitySubAppSession extends AbstractFermatSession<InstalledSubApp,CryptoCustomerCommunityModuleManager,SubAppResourcesProviderManager> {

    /**
     * Create a session for the Wallet Store SubApp
     *
     * @param errorManager             the error manager
     * @param moduleManager the module of this SubApp
     */
    public CryptoCustomerCommunitySubAppSession(InstalledSubApp subApp, ErrorManager errorManager, CryptoCustomerCommunityModuleManager moduleManager) {
        super(subApp.getAppPublicKey(), subApp, errorManager, moduleManager, null);
    }

}
