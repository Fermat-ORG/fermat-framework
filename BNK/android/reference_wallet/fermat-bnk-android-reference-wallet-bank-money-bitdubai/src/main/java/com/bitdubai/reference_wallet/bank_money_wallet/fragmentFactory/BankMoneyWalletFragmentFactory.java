package com.bitdubai.reference_wallet.bank_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.summary.AccountDetailFragment;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.home.AccountsListFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;

/**
 * Created by Guillermo on 04/12/15.
 */
public class BankMoneyWalletFragmentFactory extends FermatFragmentFactory<BankMoneyWalletSession,WalletResourcesProviderManager, BankMoneyWalletFragmentsEnumType> {
    @Override
    public AbstractFermatFragment getFermatFragment(BankMoneyWalletFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }
        switch (fragment) {
            case BNK_BANK_MONEY_WALLET_ACCOUNTS_LIST:
                return AccountsListFragment.newInstance();
            case BNK_BANK_MONEY_WALLET_ACCOUNT_DETAIL:
                return AccountDetailFragment.newInstance();
            default: throw createFragmentNotFoundException(fragment);
        }
    }

    @Override
    public BankMoneyWalletFragmentsEnumType getFermatFragmentEnumType(String key) {
        return BankMoneyWalletFragmentsEnumType.getValue(key);
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
