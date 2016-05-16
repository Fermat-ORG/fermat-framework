package com.bitdubai.sub_app.developer.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class DeveloperSubAppSession extends AbstractFermatSession<InstalledSubApp,ToolManager,SubAppResourcesProviderManager> {

    public DeveloperSubAppSession() {}

}
