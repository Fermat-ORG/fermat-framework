package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.Session;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.settings.Settings;

/**
 * CommunityUserFragmentFactory
 */
public class CommunityUserFragmentFactory extends FermatSubAppFragmentFactory<Session, Settings, FragmentFactoryEnumType> {


    @Override
    public FermatFragment getFermatFragment(FragmentFactoryEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN:
                return null; // TODO: 14/10/15 Create Main Fragment
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }

    @Override
    public FragmentFactoryEnumType getFermatFragmentEnumType(String key) {
        return FragmentFactoryEnumType.getValue(key);
    }
}
