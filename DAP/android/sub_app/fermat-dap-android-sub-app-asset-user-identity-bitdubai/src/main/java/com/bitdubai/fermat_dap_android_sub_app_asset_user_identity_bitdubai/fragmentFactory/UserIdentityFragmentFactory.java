package com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.fragments.CreateIdentityFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.fragments.IdentityListFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.preference_settings.UserIdentitySubAppSettings;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.session.UserIdentitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class UserIdentityFragmentFactory extends FermatSubAppFragmentFactory<UserIdentitySubAppSession, UserIdentitySubAppSettings, UserIdentityFragmentEnumType> {


    @Override
    public FermatFragment getFermatFragment(UserIdentityFragmentEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(UserIdentityFragmentEnumType.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT))
            return IdentityListFragment.newInstance();

        if (fragments.equals(UserIdentityFragmentEnumType.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateIdentityFragment.newInstance();


        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public UserIdentityFragmentEnumType getFermatFragmentEnumType(String key) {
        return UserIdentityFragmentEnumType.getValue(key);
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
