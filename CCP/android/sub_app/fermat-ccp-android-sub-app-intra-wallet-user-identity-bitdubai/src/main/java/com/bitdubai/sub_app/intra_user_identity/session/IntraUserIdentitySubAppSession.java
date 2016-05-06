package com.bitdubai.sub_app.intra_user_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserIdentitySubAppSession extends AbstractFermatSession<InstalledSubApp,IntraUserIdentityModuleManager,SubAppResourcesProviderManager> {

    public IntraUserIdentitySubAppSession() {}

}
