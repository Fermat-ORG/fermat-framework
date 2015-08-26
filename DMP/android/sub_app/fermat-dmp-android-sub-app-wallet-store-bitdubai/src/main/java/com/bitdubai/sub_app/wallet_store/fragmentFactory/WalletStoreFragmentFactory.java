package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.wallet_store.fragments.DetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MainActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MoreDetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.preference_settings.WalletStorePreferenceSettings;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletStoreFragmentFactory extends FermatSubAppFragmentFactory<WalletStoreSubAppSession, WalletStorePreferenceSettings, WalletStoreFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(WalletStoreFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_MAIN_ACTIVITY:
                currentFragment = MainActivityFragment.newInstance(0);
                break;
            case CWP_WALLET_STORE_DETAIL_ACTIVITY:
                currentFragment = DetailsActivityFragment.newInstance(0);
                break;
            case CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY:
                currentFragment = MoreDetailsActivityFragment.newInstance(0);
                break;


            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = MainActivityFragment.newInstance(0);
                break;
            case CWP_WALLET_STORE_FREE_FRAGMENT:
                currentFragment = DetailsActivityFragment.newInstance(0);
                break;
            case CWP_WALLET_STORE_PAID_FRAGMENT:
                currentFragment = MoreDetailsActivityFragment.newInstance(0);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletStoreFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletStoreFragmentsEnumType.getValue(key);
    }

}
