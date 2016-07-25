package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.fragments.BrowserTabFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.ConnectionsTabFragment;
import com.bitdubai.sub_app.crypto_customer_community.fragments.RequestsTabFragment;


/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerCommunityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, SubAppResourcesProviderManager, CryptoCustomerCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoCustomerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                //currentFragment = BrowserTabFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTIONS:
                //currentFragment = ConnectionsFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_DETAIL:
                currentFragment = null;
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS:
                currentFragment = RequestsTabFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD:
                currentFragment = BrowserTabFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST:
                currentFragment = ConnectionsTabFragment.newInstance();
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

}
