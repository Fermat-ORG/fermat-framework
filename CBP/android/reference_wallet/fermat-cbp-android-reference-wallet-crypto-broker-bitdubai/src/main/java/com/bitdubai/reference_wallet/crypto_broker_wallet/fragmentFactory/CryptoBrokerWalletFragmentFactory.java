package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.MainFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.preference_settings.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoBrokerWalletFragmentFactory implements WalletFragmentFactory{

    @Override
    public Fragment getFragment(String code, WalletSession walletSession, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        CryptoBrokerWalletFragmentsEnumType fragment = CryptoBrokerWalletFragmentsEnumType.getValue(code);
        switch (fragment) {
            case MAIN_FRAGMET:
                currentFragment = MainFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragment.toString(), "Swith failed");
        }
        return currentFragment;
    }
}
