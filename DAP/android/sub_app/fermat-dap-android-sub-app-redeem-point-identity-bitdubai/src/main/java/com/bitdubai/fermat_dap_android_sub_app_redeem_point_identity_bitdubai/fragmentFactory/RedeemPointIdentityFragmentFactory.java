package com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.fragments.CreateIdentityFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.fragments.IdentityListFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.preference_settings.RedeemPointIdentitySubAppSettings;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.session.RedeemPointIdentitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class RedeemPointIdentityFragmentFactory extends FermatSubAppFragmentFactory<RedeemPointIdentitySubAppSession, RedeemPointIdentitySubAppSettings, RedeemPointIdentityFragmentEnumType> {


    @Override
    public FermatFragment getFermatFragment(RedeemPointIdentityFragmentEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(RedeemPointIdentityFragmentEnumType.DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT))
            return IdentityListFragment.newInstance();

        if (fragments.equals(RedeemPointIdentityFragmentEnumType.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateIdentityFragment.newInstance();


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
