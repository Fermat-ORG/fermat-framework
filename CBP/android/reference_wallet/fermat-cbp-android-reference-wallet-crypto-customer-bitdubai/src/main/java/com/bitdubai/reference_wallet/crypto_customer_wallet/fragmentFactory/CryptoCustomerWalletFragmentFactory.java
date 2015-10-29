package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.MainFragment;

import static com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentsEnumType.MAIN_FRAGMENT;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerWalletFragmentFactory implements WalletFragmentFactory {

    @Override
    public Fragment getFragment(String code, WalletSession walletSession, WalletSettings WalletSettings, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {

        CryptoCustomerWalletFragmentsEnumType fragment = CryptoCustomerWalletFragmentsEnumType.getValue(code);
        if (fragment == MAIN_FRAGMENT) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragment);
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
