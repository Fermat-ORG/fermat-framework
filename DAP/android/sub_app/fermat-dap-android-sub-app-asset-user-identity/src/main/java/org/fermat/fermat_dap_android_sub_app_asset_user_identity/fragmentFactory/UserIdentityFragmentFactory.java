package org.fermat.fermat_dap_android_sub_app_asset_user_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import org.fermat.fermat_dap_android_sub_app_asset_user_identity.fragments.CreateUserIdentityFragment;
import org.fermat.fermat_dap_android_sub_app_asset_user_identity.fragments.GeolocationUserIdentityFragment;
import org.fermat.fermat_dap_android_sub_app_asset_user_identity.fragments.IdentityListFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class UserIdentityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession, ResourceProviderManager, UserIdentityFragmentEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(UserIdentityFragmentEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(UserIdentityFragmentEnumType.DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT))
            return IdentityListFragment.newInstance();

        if (fragments.equals(UserIdentityFragmentEnumType.DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT))
            return CreateUserIdentityFragment.newInstance();

        if (fragments.equals(UserIdentityFragmentEnumType.DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_FRAGMENT))
            return GeolocationUserIdentityFragment.newInstance();


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
