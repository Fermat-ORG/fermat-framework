package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommuinityHomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommuinityGroupUsersFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommuinityUsersFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommunityConnectionsListFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommunityGroupFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UserCommunityNotificationsFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments.UsersCommunityConnectionOtherProfileFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * CommunityUserFragmentFactory
 */
public class CommunityUserFragmentFactory extends FermatFragmentFactory<AssetUserCommunitySubAppSession, SubAppResourcesProviderManager, FragmentFactoryEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(FragmentFactoryEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_MAIN:
                return UserCommuinityHomeFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_FRAGMENT:
                return UserCommunityGroupFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT:
                return UserCommuinityGroupUsersFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS:
                return UserCommuinityUsersFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT:
                return UsersCommunityConnectionOtherProfileFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_FRAGMENT:
                return UserCommunityConnectionsListFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT:
                return UserCommunityNotificationsFragment.newInstance();
            case DAP_ASSET_USER_COMMUNITY_CONNECTION_LIST_OTHER_PROFILE_FRAGMENT:
                return UsersCommunityConnectionOtherProfileFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-sub-app-asset-user-community", "fragment not found");
        }
    }

    @Override
    public FragmentFactoryEnumType getFermatFragmentEnumType(String key) {
        return FragmentFactoryEnumType.getValue(key);
    }
}
