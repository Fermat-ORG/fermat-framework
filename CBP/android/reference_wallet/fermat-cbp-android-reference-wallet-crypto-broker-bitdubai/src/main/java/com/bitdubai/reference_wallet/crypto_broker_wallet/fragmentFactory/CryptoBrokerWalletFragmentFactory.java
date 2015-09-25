package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.MainFragment;

import static com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoBrokerWalletFragmentFactory implements WalletFragmentFactory{

    @Override
    public Fragment getFragment(String code, WalletSession walletSession, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
        CryptoBrokerWalletFragmentsEnumType fragment = CryptoBrokerWalletFragmentsEnumType.getValue(code);

        if (fragment == MAIN_FRAGMET) {
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
