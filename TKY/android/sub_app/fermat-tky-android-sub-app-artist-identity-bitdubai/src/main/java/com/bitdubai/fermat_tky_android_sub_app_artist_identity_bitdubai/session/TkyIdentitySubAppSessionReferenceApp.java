package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */

public class TkyIdentitySubAppSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp, TokenlyArtistIdentityManagerModule, SubAppResourcesProviderManager> {
    public TkyIdentitySubAppSessionReferenceApp(){
    }
}
