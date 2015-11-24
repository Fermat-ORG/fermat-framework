package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCreateBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletManagerImpl implements BankMoneyWalletManager {
    @Override
    public BankMoneyWallet loadBankMoneyWallet(String walletPublicKey) throws CantLoadBankMoneyWalletException {
        return null;
    }

    @Override
    public void createBankMoneyWallet(String walletPublicKey) throws CantCreateBankMoneyWalletException {

    }
}
