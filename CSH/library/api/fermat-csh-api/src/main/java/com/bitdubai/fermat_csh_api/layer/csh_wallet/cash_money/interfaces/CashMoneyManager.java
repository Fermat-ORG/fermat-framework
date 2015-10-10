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
                 final String cashTransactionId
                ,final String publicKeyActorFrom
                ,final String publicKeyActorTo
                ,final String status
                ,final String balanceType
                ,final String transactionType
                ,final float amount
                ,final String cashCurrencyType
                ,final String cashReference
                ,final long runningBookBalance
                ,final long runningAvailableBalance
                ,final long timestamp
                ,final String memo
        ) throws CantCreateCashMoneyException;

        CashMoney loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException;

        void createCashMoney (String walletPublicKey) throws CantCreateCashMoneyException;
}
