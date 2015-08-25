package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_store.fragments.DetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MoreDetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.old.AcceptedNearbyFragment;
import com.bitdubai.sub_app.wallet_store.fragments.old.FreeFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MainActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.old.PaidFragment;
import com.bitdubai.sub_app.wallet_store.fragments.old.SearchFragment;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletStoreFragmentFactory implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code                           the reference used to identify the fragment
     * @param subAppsSession                 the session of the SubApp (Contains Module Manager, Error Manager, and other data)
     * @param subAppSettingsManager          the manager for the SubApp's Settings
     * @param subAppResourcesProviderManager the manager for the SubApp's Resources Provider
     * @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, SubAppsSession subAppsSession, SubAppSettingsManager subAppSettingsManager, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException {
        Fragment currentFragment;
        WalletStoreSubAppSession session = (WalletStoreSubAppSession) subAppsSession;

        Fragments fragment = Fragments.getValueFromString(code);
        switch (fragment) {
            case CWP_WALLET_STORE_MAIN_ACTIVITY:
                currentFragment = MainActivityFragment.newInstance(0, session);
                break;
            case CWP_WALLET_STORE_DETAIL_ACTIVITY:
                currentFragment = DetailsActivityFragment.newInstance(0, session);
                break;
            case CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY:
                currentFragment = MoreDetailsActivityFragment.newInstance(0, session);
                break;


            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = MainActivityFragment.newInstance(0, session);
                break;
            case CWP_WALLET_STORE_FREE_FRAGMENT:
                currentFragment = DetailsActivityFragment.newInstance(0, session);
                break;
            case CWP_WALLET_STORE_PAID_FRAGMENT:
                currentFragment = MoreDetailsActivityFragment.newInstance(0, session);
                break;

            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), code, "Switch failed");
        }
        return currentFragment;
    }

}
