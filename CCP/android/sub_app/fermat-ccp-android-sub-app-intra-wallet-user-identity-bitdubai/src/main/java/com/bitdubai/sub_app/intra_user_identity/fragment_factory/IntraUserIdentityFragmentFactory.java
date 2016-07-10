package com.bitdubai.sub_app.intra_user_identity.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_identity.fragments.CreateIntraUserIdentityFragment;
import com.bitdubai.sub_app.intra_user_identity.fragments.GeolocationIntraUserIdentityFragment;
import com.bitdubai.sub_app.intra_user_identity.fragments.IntraUserIdentityListFragment;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSessionReferenceApp;



/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class IntraUserIdentityFragmentFactory extends FermatFragmentFactory<IntraUserIdentitySubAppSessionReferenceApp,SubAppResourcesProviderManager,IntraUserIdentityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(IntraUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT))
            return IntraUserIdentityListFragment.newInstance();

        if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateIntraUserIdentityFragment.newInstance();

        if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_INTRA_IDENTITY_GEOLOCATION_IDENTITY))
            return GeolocationIntraUserIdentityFragment.newInstance();


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
