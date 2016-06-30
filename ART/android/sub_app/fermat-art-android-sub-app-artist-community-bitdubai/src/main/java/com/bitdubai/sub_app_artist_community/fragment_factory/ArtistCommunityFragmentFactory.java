package com.bitdubai.sub_app_artist_community.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app_artist_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app_artist_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app_artist_community.fragments.ConnectionsFragment;
import com.bitdubai.sub_app_artist_community.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app_artist_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app_artist_community.fragments.ListUserIdentiesFragment;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ArtistCommunityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<FanCommunityModuleManager>,SubAppResourcesProviderManager,ArtistCommunityFragmentEnumType> {
    @Override
    protected AbstractFermatFragment getFermatFragment(ArtistCommunityFragmentEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;
        switch (fragments) {
            /*case ART_ARTIST_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;*/
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTIONS:
                currentFragment = ConnectionsFragment.newInstance();
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_DETAIL:
                currentFragment = null;
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_NOTIFICATIONS:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_FRIEND_LIST:
                currentFragment = ConnectionsListFragment.newInstance();
                break;
            case ART_SUB_APP_ARTIST_COMMUNITY_LOCAL_IDENTITIES_LIST:
                currentFragment = ListUserIdentiesFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException(
                        fragments.toString(),
                        "Switch failed");
        }
        return currentFragment;
    }

    @Override
    public ArtistCommunityFragmentEnumType getFermatFragmentEnumType(String key) {
        return ArtistCommunityFragmentEnumType.getValue(key);
    }
}
