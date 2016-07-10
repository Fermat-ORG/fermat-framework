package com.bitdubai.sub_app.fan_community.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class FanCommunitySubAppSessionReferenceApp extends
        AbstractReferenceAppFermatSession<
                        InstalledSubApp,
                        FanCommunityModuleManager,
                        SubAppResourcesProviderManager> {

}
