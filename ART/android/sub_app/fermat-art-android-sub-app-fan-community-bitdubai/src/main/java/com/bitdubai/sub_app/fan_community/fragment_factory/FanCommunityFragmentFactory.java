package com.bitdubai.sub_app.fan_community.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.fan_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app.fan_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.fan_community.fragments.ConnectionsFragment;
import com.bitdubai.sub_app.fan_community.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app.fan_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.fan_community.fragments.ListUserIdentiesFragment;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSession;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class FanCommunityFragmentFactory extends
        FermatFragmentFactory<
                FanCommunitySubAppSession,
                SubAppResourcesProviderManager,
                FanCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(FanCommunityFragmentsEnumType fragments) throws
            FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;

        switch (fragments) {
            /*case ART_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;*/
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTIONS:
                currentFragment = ConnectionsFragment.newInstance();
                break;
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTION_DETAIL:
                //currentFragment = null;
                break;
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTION_NOTIFICATIONS:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTION_OTHER_PROFILE:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case ART_SUB_APP_FAN_COMMUNITY_CONNECTION_FRIEND_LIST:
                currentFragment = ConnectionsListFragment.newInstance();
                break;
            case ART_SUB_APP_FAN_COMMUNITY_LOCAL_IDENTITIES_LIST:
                currentFragment = ListUserIdentiesFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException(
                        fragments.toString(),
                        "Switch failed"
                );
        }
        return currentFragment;
    }

    @Override
    public FanCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return FanCommunityFragmentsEnumType.getValue(key);
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
        return new FragmentNotFoundException(
                "Fragment not found",
                new Exception(),
                context,
                possibleReason);
    }
}

