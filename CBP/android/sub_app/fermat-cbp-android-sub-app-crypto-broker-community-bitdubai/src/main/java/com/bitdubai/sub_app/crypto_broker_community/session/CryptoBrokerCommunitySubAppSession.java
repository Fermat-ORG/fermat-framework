package com.bitdubai.sub_app.crypto_broker_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public final class CryptoBrokerCommunitySubAppSession extends AbstractFermatSession<InstalledSubApp, IntraUserModuleManager, SubAppResourcesProviderManager> implements SubAppsSession {

    public CryptoBrokerCommunitySubAppSession(final InstalledSubApp        subApp                ,
                                              final ErrorManager           errorManager          ,
                                              final IntraUserModuleManager intraUserModuleManager) {

        super(
                subApp.getAppPublicKey(),
                subApp,errorManager     ,
                intraUserModuleManager  ,
                null
        );
    }

    @Override
    public final InstalledSubApp getSubAppSessionType() {

        return getFermatApp();
    }

}
