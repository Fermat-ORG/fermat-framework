package com.bitdubai.sup_app.tokenly_fan_user_identity.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sup_app.tokenly_fan_user_identity.fragments.CreateTokenlyFanUserIdentityFragment;
import com.bitdubai.sup_app.tokenly_fan_user_identity.session.TokenlyFanUserIdentitySubAppSessionReferenceApp;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 22/03/16.
 */
public class TokenlyFanUserIdentityFragmentFactory extends FermatFragmentFactory<TokenlyFanUserIdentitySubAppSessionReferenceApp,SubAppResourcesProviderManager,TokenlyFanUserIdentityFragmentsEnumType> {
    @Override
    protected AbstractFermatFragment getFermatFragment(TokenlyFanUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
        if(fragments.equals(TokenlyFanUserIdentityFragmentsEnumType.TKY_SUB_APP_FAN_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateTokenlyFanUserIdentityFragment.newInstance();
        return null;
    }

    @Override
    public TokenlyFanUserIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return TokenlyFanUserIdentityFragmentsEnumType.getValue(key);
    }
}
