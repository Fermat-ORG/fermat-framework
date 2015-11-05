package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import java.util.UUID;

/**
 * Created by francisco on 02/11/15.
 */
public class CashMoneyConstructor {
    UUID cashTransactionId;
    String walletKeyBroker;
    String publicKeyCustomer;
    String publicKeyBroker;
    String balanceType;
    String transactionType;
    double amount;
    String cashCurrencyType;
    String cashReference;
    long runningBookBalance;
    long runningAvailableBalance;
    long timeStamp;
    String memo;
    String status;

    public CashMoneyConstructor(UUID cashTransactionId, String walletKeyBroker, String publicKeyCustomer, String publicKeyBroker, String balanceType, String transactionType, double amount, String cashCurrencyType, String cashReference, long runningBookBalance, long runningAvailableBalance, long timeStamp, String memo, String status) {
        this.cashTransactionId = cashTransactionId;
        this.walletKeyBroker = walletKeyBroker;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.cashReference = cashReference;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.status = status;
    }
}
