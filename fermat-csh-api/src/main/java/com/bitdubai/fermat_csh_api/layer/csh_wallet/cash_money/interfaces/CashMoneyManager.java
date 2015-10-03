package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantGenerateBalanceCashMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface CashMoneyManager {

    List<CashMoney> getTransactionsCashMoney() throws CantTransactionCashMoneyException;

    CashMoney createCashMoney(
         final String publicKeyBroker
        ,final String walletId
    ) throws CantCreateCashMoneyException;

    void generateBankMoneyBalance() throws CantGenerateBalanceCashMoneyException;
}
