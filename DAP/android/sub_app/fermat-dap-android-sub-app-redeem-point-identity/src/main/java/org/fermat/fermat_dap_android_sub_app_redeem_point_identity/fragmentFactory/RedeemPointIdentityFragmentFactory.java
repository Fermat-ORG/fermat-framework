package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;


import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragments.CreateRedeemPointIdentityFragment;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragments.IdentityListFragment;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.session.RedeemPointIdentitySubAppSessionReferenceApp;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragments.GeolocationRedeemPointIdentityFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class RedeemPointIdentityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession, ResourceProviderManager, RedeemPointIdentityFragmentEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(RedeemPointIdentityFragmentEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(RedeemPointIdentityFragmentEnumType.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT))
            return IdentityListFragment.newInstance();

        if (fragments.equals(RedeemPointIdentityFragmentEnumType.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateRedeemPointIdentityFragment.newInstance();

        if (fragments.equals(RedeemPointIdentityFragmentEnumType.DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_FRAGMENT))
            return GeolocationRedeemPointIdentityFragment.newInstance();


        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public RedeemPointIdentityFragmentEnumType getFermatFragmentEnumType(String key) {
        return RedeemPointIdentityFragmentEnumType.getValue(key);
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
