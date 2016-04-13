package com.bitdubai.sub_app.art_fan_identity.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import com.bitdubai.sub_app.art_fan_identity.fragments.CreateArtFanUserIdentityFragment;
import com.bitdubai.sub_app.art_fan_identity.sessions.ArtFanUserIdentitySubAppSession;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public class ArtFanUserIdentityFragmentFactory extends
        FermatFragmentFactory<
                ArtFanUserIdentitySubAppSession,
                SubAppResourcesProviderManager,
                ArtFanUserIdentityFragmentsEnumType> {

    /**
     * This method returns an AbstractFermatFragment object
     * @param fragments
     * @return
     * @throws FragmentNotFoundException
     */
    @Override
    protected AbstractFermatFragment getFermatFragment(
            ArtFanUserIdentityFragmentsEnumType fragments) throws
            FragmentNotFoundException {
        if(fragments.equals(ArtFanUserIdentityFragmentsEnumType.ART_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateArtFanUserIdentityFragment.newInstance();
        return null;
    }

    @Override
    public ArtFanUserIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return ArtFanUserIdentityFragmentsEnumType.getValue(key);
    }
}