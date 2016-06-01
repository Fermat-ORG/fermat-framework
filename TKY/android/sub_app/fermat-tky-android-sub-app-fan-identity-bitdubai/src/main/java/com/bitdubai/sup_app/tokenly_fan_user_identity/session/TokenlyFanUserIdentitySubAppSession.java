package com.bitdubai.sup_app.tokenly_fan_user_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanIdentityManagerModule;

import java.io.Serializable;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 22/03/16.
 */
public class TokenlyFanUserIdentitySubAppSession extends
        AbstractFermatSession<InstalledSubApp,TokenlyFanIdentityManagerModule,SubAppResourcesProviderManager> implements Serializable {
    public TokenlyFanUserIdentitySubAppSession() {
    }
}
