package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCreateBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface BankMoneyWalletManager extends FermatManager {


    public BankMoneyWallet loadBankMoneyWallet(String walletPublicKey) throws CantLoadBankMoneyWalletException;

    /*public void createBankMoneyWallet (String walletPublicKey) throws CantCreateBankMoneyWalletException;*/
}
