package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.fragments.BrowserTabFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionOtherProfileFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.ConnectionsTabFragment;
import com.bitdubai.sub_app.crypto_broker_community.fragments.NotificationsTabFragment;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CryptoBrokerCommunityFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>, SubAppResourcesProviderManager, CryptoBrokerCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoBrokerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment;

        switch (fragments) {
            case CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS:
                currentFragment = NotificationsTabFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE:
                currentFragment = ConnectionOtherProfileFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD:
                currentFragment = BrowserTabFragment.newInstance();
                break;
            case CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST:
                currentFragment = ConnectionsTabFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException(fragments.toString(), "Switch failed");
        }
        return currentFragment;
    }

    @Override
    public CryptoBrokerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerCommunityFragmentsEnumType.getValue(key);
    }

}
