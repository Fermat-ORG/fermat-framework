package com.bitdubai.reference_wallet.cash_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.fragments.home.BalanceSummaryFragment;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class CashMoneyWalletFragmentFactory extends FermatFragmentFactory<CashMoneyWalletSession, WalletSettings, WalletResourcesProviderManager, CashMoneyWalletFragmentsEnumType> {
    @Override
    public FermatWalletFragment getFermatFragment(CashMoneyWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }
        switch (fragment) {
            case CSH_CASH_MONEY_WALLET_BALANCE_SUMMARY:
                return BalanceSummaryFragment.newInstance();
            default: throw createFragmentNotFoundException(fragment);
        }
    }

    @Override
    public CashMoneyWalletFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CashMoneyWalletFragmentsEnumType.getValue(key);
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
