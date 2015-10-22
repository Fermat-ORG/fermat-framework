package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.HomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.settings.Settings;

/**
 * CommunityUserFragmentFactory
 */
public class CommunityUserFragmentFactory extends FermatSubAppFragmentFactory<AssetUserCommunitySubAppSession, Settings, FragmentFactoryEnumType> {


    @Override
    public FermatFragment getFermatFragment(FragmentFactoryEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN:
                return HomeFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-user", "fragment not found");
        }
    }

    @Override
    public FragmentFactoryEnumType getFermatFragmentEnumType(String key) {
        return FragmentFactoryEnumType.getValue(key);
    }
}
