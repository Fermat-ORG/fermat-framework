package com.bitdubai.reference_wallet.bank_money_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.account_management.AddAccountFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.account_management.EditAccountFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.home.AccountsListFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.setup.SetupFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.details.AccountDetailFragment;
import com.bitdubai.reference_wallet.bank_money_wallet.fragments.details.TransactionDetailFragment;

/**
 * Created by Guillermo on 04/12/15.
 */
public class BankMoneyWalletFragmentFactory extends FermatFragmentFactory<ReferenceAppFermatSession<BankMoneyWalletModuleManager>, WalletResourcesProviderManager, BankMoneyWalletFragmentsEnumType> {
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
            case BNK_BANK_MONEY_WALLET_ADD_ACCOUNT:
                return AddAccountFragment.newInstance();
            case BNK_BANK_MONEY_WALLET_EDIT_ACCOUNT:
                return EditAccountFragment.newInstance();
            case BNK_BANK_MONEY_WALLET_SETUP:
                return SetupFragment.newInstance();
            case BNK_BANK_MONEY_WALLET_TRANSACTION_DETAIL:
                return TransactionDetailFragment.newInstance();
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
