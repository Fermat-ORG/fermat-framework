package com.bitdubai.sub_app.intra_user_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserSubAppSession extends AbstractFermatSession<InstalledSubApp,IntraUserModuleManager,SubAppResourcesProviderManager>{

    public static final String BASIC_DATA = "catalog item";
    public static final String PREVIEW_IMGS = "preview images";
    public static final String DEVELOPER_NAME = "developer name";

    public IntraUserSubAppSession() {}
}
