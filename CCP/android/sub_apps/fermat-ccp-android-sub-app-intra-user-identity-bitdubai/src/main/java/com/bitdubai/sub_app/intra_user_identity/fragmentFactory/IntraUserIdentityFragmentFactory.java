package com.bitdubai.sub_app.intra_user_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

import com.bitdubai.sub_app.intra_user_identity.fragments.CreateIntraUserIdentityFragment;
import com.bitdubai.sub_app.intra_user_identity.fragments.IntraUserIdentityListFragment;
import com.bitdubai.sub_app.intra_user_identity.preference_settings.IntraUserIdentityPreferenceSettings;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSession;

import static com.bitdubai.sub_app.intra_user_identity.fragmentFactory.IntraUserIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT;
import static com.bitdubai.sub_app.intra_user_identity.fragmentFactory.IntraUserIdentityFragmentsEnumType.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class IntraUserIdentityFragmentFactory extends FermatSubAppFragmentFactory<IntraUserIdentitySubAppSession, IntraUserIdentityPreferenceSettings, IntraUserIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(IntraUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_MAIN_FRAGMENT)
            return IntraUserIdentityListFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY_FRAGMENT)
            return CreateIntraUserIdentityFragment.newInstance();


        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public IntraUserIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return IntraUserIdentityFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }

}
