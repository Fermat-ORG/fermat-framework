package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantLoadCashMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface CashMoneyManager {

        List<CashMoney> getTransactionsCashMoney() throws CantTransactionCashMoneyException;

        CashMoney registerCashMoney(
                 final String getCashTransactionId
                ,final String getPublicKeyCustomer
                ,final String getPublicKeyBroker
                ,final String getStatus
                ,final String getBalanceType
                ,final String getTransactionType
                ,final float getAmount
                ,final String getCashCurrencyType
                ,final String getCashReference
                ,final long getTimestamp
                ,final String getMemo
        ) throws CantCreateCashMoneyException;

        CashMoney loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException;

        void createCashMoney (String walletPublicKey) throws CantCreateCashMoneyException;
}
