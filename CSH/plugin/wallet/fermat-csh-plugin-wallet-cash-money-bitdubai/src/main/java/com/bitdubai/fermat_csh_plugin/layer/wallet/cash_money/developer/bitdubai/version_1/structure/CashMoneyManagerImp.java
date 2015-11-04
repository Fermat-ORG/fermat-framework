package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantLoadCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoney;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyManager;

import java.util.List;

/**
 * Created by francisco on 03/11/15.
 */
public class CashMoneyManagerImp implements CashMoneyManager {

    String cashTransactionId;
    String publicKeyActorFrom;
    String publicKeyActorTo;
    String status;
    String balanceType;
    String transactionType;
    double amount;
    String cashCurrencyType;
    String cashReference;
    long runningBookBalance;
    long runningAvailableBalance;
    long timestamp;
    String mem;

    public CashMoneyManagerImp(String cashTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String status, String balanceType, String transactionType, double amount, String cashCurrencyType, String cashReference, long runningBookBalance, long runningAvailableBalance, long timestamp, String mem) {
        this.cashTransactionId = cashTransactionId;
        this.publicKeyActorFrom = publicKeyActorFrom;
        this.publicKeyActorTo = publicKeyActorTo;
        this.status = status;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.cashReference = cashReference;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timestamp = timestamp;
        this.mem = mem;
    }

    @Override
    public List<CashMoney> getTransactionsCashMoney() throws CantTransactionCashMoneyException {
        return null;
    }

    @Override
    public CashMoney registerCashMoney(String cashTransactionId,
                                       String publicKeyActorFrom,
                                       String publicKeyActorTo,
                                       String status,
                                       String balanceType,
                                       String transactionType,
                                       double amount,
                                       String cashCurrencyType,
                                       String cashReference,
                                       long runningBookBalance,
                                       long runningAvailableBalance,
                                       long timestamp,
                                       String memo)
            throws CantCreateCashMoneyException {
        return null;
    }

    @Override
    public CashMoney loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException {
        return null;
    }

    @Override
    public void createCashMoney(String walletPublicKey) throws CantCreateCashMoneyException {

    }
}
