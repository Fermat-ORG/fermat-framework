package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionNotificationsFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;


/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerCommunityFragmentFactory extends FermatFragmentFactory<CryptoCustomerCommunitySubAppSession,SubAppResourcesProviderManager, CryptoCustomerCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoCustomerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                //currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTIONS:
                //currentFragment = ConnectionsFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_DETAIL:
                currentFragment = null;
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS:
                currentFragment = ConnectionNotificationsFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD:
                currentFragment = ConnectionsWorldFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST:
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
    public CryptoCustomerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerCommunityFragmentsEnumType.getValue(key);
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
