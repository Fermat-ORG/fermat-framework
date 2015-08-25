package com.bitdubai.sub_app.wallet_store.FragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.wallet_store.fragment.AcceptedNearbyFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AllFragment;
import com.bitdubai.sub_app.wallet_store.fragment.FreeFragment;
import com.bitdubai.sub_app.wallet_store.fragment.PaidFragment;
import com.bitdubai.sub_app.wallet_store.fragment.SearchFragment;
import com.bitdubai.sub_app.wallet_store.preference_settings.WalletStorePreferenceSettings;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletStoreFragmentFactory extends FermatSubAppFragmentFactory<WalletStoreSubAppSession,WalletStorePreferenceSettings,WalletStoreFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(WalletStoreFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT:
                currentFragment =  AcceptedNearbyFragment.newInstance(0, null);
                break;
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = AllFragment.newInstance(0,null);
                break;
            case CWP_WALLET_STORE_FREE_FRAGMENT:
                currentFragment =  FreeFragment.newInstance(0, null);
                break;

            case CWP_WALLET_STORE_PAID_FRAGMENT:
                currentFragment =  PaidFragment.newInstance(0, null);
                break;
            case CWP_WALLET_STORE_SEARCH_MODE:
                currentFragment = SearchFragment.newInstance(0, null);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),fragments.toString(),"Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletStoreFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletStoreFragmentsEnumType.getValue(key);
    }

}
