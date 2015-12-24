package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionsFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.crypto_broker_community.preference_settings.CryptoBrokerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CryptoBrokerCommunityFragmentFactory extends FermatFragmentFactory<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager, CryptoBrokerCommunityFragmentsEnumType> {


   /* @Override
    public FermatFragment getFermatFragment(final CryptoBrokerCommunityFragmentsEnumType fragment) throws FragmentNotFoundException {

        switch (fragment) {

            case CONNECTIONS               : return ConnectionsFragment            .newInstance();
            case CONNECTIONS_LIST          : return ConnectionsListFragment        .newInstance();
            case CONNECTIONS_NOTIFICATIONS : return ConnectionNotificationsFragment.newInstance();
            case CONNECTIONS_OTHER_PROFILE : return ConnectionOtherProfileFragment .newInstance();
            case CONNECTIONS_REGISTER_USER : return RegisterUserFragment           .newInstance();
            case CONNECTIONS_REQUEST       : return RequestConnectionsFragment     .newInstance();
            case CONNECTIONS_SETTINGS      : return ConnectionSettingsFragment     .newInstance();
            case CONNECTIONS_WORLD         : return ConnectionsWorldFragment       .newInstance();
            case MAIN                      : return ConnectionsWorldFragment       .newInstance();

            default:
                throw new FragmentNotFoundException(
                        fragment.toString(),
                        "Switch failed due to missing 'case' sentence for the requested fragment."
                );
        }
    }*/


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoBrokerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT:
                currentFragment = ConnectionsFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_DETAIL_FRAGMENT:
                currentFragment = null;
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT:
                currentFragment = ConnectionsListFragment.newInstance();
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
    public CryptoBrokerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerCommunityFragmentsEnumType.getValue(key);
    }

}
