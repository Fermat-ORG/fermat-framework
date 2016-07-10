package com.bitdubai.sub_app_artist_community.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 07/04/16.
 */
public class ArtistSubAppSessionReferenceApp extends AbstractReferenceAppFermatSession<
        InstalledSubApp,
        ArtistCommunitySubAppModuleManager,
        SubAppResourcesProviderManager> {

    public ArtistSubAppSessionReferenceApp() {
    }
}
