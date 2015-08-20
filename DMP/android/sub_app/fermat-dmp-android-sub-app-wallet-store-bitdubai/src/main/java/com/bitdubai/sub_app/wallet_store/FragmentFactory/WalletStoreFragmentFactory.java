package com.bitdubai.sub_app.wallet_store.FragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_store.fragment.AcceptedNearbyFragment;
import com.bitdubai.sub_app.wallet_store.fragment.AllFragment;
import com.bitdubai.sub_app.wallet_store.fragment.FreeFragment;
import com.bitdubai.sub_app.wallet_store.fragment.PaidFragment;
import com.bitdubai.sub_app.wallet_store.fragment.SearchFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletStoreFragmentFactory implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory {


    public WalletStoreFragmentFactory(){

    }


    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *  @param code                           the reference used to identify the fragment
     * @param subAppsSession
     * @param subAppSettingsManager
     * @param subAppResourcesProviderManager @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, SubAppsSession subAppsSession, SubAppSettingsManager subAppSettingsManager, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException {
        Fragment currentFragment = null;

        Fragments fragment = Fragments.getValueFromString(code);
        switch (fragment){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT:
                currentFragment =  AcceptedNearbyFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = AllFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_STORE_FREE_FRAGMENT:
                currentFragment =  FreeFragment.newInstance(0, subAppsSession);
                break;

            case CWP_WALLET_STORE_PAID_FRAGMENT:
                currentFragment =  PaidFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_STORE_SEARCH_MODE:
                currentFragment = SearchFragment.newInstance(0, subAppsSession);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),code,"Swith failed");
        }
        return currentFragment;
    }

}
