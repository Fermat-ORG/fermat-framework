package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Artist.ArtistIdentityManagerModule;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 17/03/16.
 */
public class ArtistIdentitySubAppSessionReferenceApp extends
        AbstractReferenceAppFermatSession<
                        InstalledSubApp,
                        ArtistIdentityManagerModule,
                        SubAppResourcesProviderManager> {
    public ArtistIdentitySubAppSessionReferenceApp(){
        /**
         * This method is implemented only for use in the proper implementation.
         * Is left empty on purpose.
         */
    }
}
