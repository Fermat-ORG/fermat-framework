package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments.HomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.sessions.AssetRedeemPointCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.settings.Settings;

/**
 * CommunityAssetRedeemPointFragmentFactory
 */
public class AssetRedeemPointCommunityFragmentFactory extends FermatSubAppFragmentFactory<AssetRedeemPointCommunitySubAppSession, Settings, FragmentFactoryEnumType> {

    @Override
    public FermatFragment getFermatFragment(FragmentFactoryEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_MAIN:
                return HomeFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-sub-app-asset-redeem-point", "fragment not found");
        }
    }

    @Override
    public FragmentFactoryEnumType getFermatFragmentEnumType(String key) {
        return FragmentFactoryEnumType.getValue(key);
    }
}
