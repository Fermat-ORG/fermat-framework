package com.bitdubai.reference_wallet.cash_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.fragments.home.HomeFragment;
import com.bitdubai.reference_wallet.cash_money_wallet.fragments.setup.SetupFragment;
import com.bitdubai.reference_wallet.cash_money_wallet.fragments.transactionDetail.TransactionDetailFragment;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class CashMoneyWalletFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<CashMoneyWalletModuleManager>, WalletResourcesProviderManager, CashMoneyWalletFragmentsEnumType> {
    @Override
    public AbstractFermatFragment getFermatFragment(CashMoneyWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }
        switch (fragment) {
            case CSH_CASH_MONEY_WALLET_BALANCE_SUMMARY:
                return HomeFragment.newInstance();
            case CSH_CASH_MONEY_WALLET_TRANSACTION_DETAIL:
                return TransactionDetailFragment.newInstance();
            case CSH_CASH_MONEY_WALLET_SETUP:
                return SetupFragment.newInstance();
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
