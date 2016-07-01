package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragments.TokenlyArtistIdentityCreateProfile;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragments.TkyIdentityHomeFragment;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.TkyIdentitySubAppSessionReferenceApp;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */


public class TkyArtistIdentityFragmentFactory extends FermatFragmentFactory<TkyIdentitySubAppSessionReferenceApp, SubAppResourcesProviderManager, TkyArtistIdentityEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(TkyArtistIdentityEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case TKY_ARTIST_IDENTITY_ACTIVITY_CREATE_PROFILE:
                return TokenlyArtistIdentityCreateProfile.newInstance();

            case ART_SUB_APP_FAN_IDENTITY_TEST_FRAGMENT:
                return TkyIdentityHomeFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-tky-android-sub-app-artist-community", "fragment not found");
        }
    }

    @Override
    public TkyArtistIdentityEnumType getFermatFragmentEnumType(String key) {
        return TkyArtistIdentityEnumType.getValue(key);
    }
}