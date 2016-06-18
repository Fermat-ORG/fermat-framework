package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.fragments.CreateArtistIndetityFragment;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.session.ArtistIdentitySubAppSessionReferenceApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 17/03/16.
 */


public class ArtistIdentityFragmentFactory extends FermatFragmentFactory<ArtistIdentitySubAppSessionReferenceApp, SubAppResourcesProviderManager, ArtistIdentityEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(ArtistIdentityEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case ART_SUB_APP_ARTIST_IDENTITY_CREATE_PROFILE:
                return CreateArtistIndetityFragment.newInstance();

            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-art-android-sub-app-artist-community", "fragment not found");
        }
    }

    @Override
    public ArtistIdentityEnumType getFermatFragmentEnumType(String key) {
        return ArtistIdentityEnumType.getValue(key);
    }
}