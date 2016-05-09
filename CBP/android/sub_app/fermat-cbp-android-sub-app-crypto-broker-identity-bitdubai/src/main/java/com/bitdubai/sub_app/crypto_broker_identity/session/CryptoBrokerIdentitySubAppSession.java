package com.bitdubai.sub_app.crypto_broker_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class CryptoBrokerIdentitySubAppSession extends AbstractFermatSession<InstalledSubApp, CryptoBrokerIdentityModuleManager, SubAppResourcesProviderManager> {
    public static final String IDENTITY_INFO = "CRYPTO_IDENTITY_INFO";
    public CryptoBrokerIdentitySubAppSession() {

    }
}
