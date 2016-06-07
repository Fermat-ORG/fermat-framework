package com.bitdubai.sub_app.wallet_publisher.FragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletPublisherFragmentFactory extends FermatFragmentFactory<WalletPublisherSubAppSession, SubAppResourcesProviderManager, WalletPublisherFragmentsEnumType> {


    public WalletPublisherFragmentFactory() {

    }

    @Override
    public AbstractFermatFragment getFermatFragment(WalletPublisherFragmentsEnumType fragments) throws FragmentNotFoundException {
        AbstractFermatFragment currentFragment = null;

        switch (fragments) {
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_PUBLISHER_MAIN_FRAGMENT:
                currentFragment = MainFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletPublisherFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletPublisherFragmentsEnumType.getValue(key);
    }


}
