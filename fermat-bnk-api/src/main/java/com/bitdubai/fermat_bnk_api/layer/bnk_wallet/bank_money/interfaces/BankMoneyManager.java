package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCreateBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGenerateBalanceBankMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface BankMoneyManager {

    List<BankMoney> getTransactionsBankMoney() throws CantTransactionBankMoneyException;

    BankMoney registerBankMoney(
         final String publicKeyBroker
        ,final String walletId
    ) throws CantCreateBankMoneyException;

    void generateBankMoneyBalance() throws CantGenerateBalanceBankMoneyException;
}
