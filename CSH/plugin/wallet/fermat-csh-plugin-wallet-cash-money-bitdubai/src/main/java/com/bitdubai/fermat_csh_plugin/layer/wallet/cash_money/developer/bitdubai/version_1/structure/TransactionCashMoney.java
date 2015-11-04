package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransaction;

import java.util.UUID;

/**
 * Created by francisco on 22/10/15.
 */
public class TransactionCashMoney implements CashMoneyTransaction {

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

    public TransactionCashMoney(UUID cashTransactionId, String walletKeyBroker, String publicKeyCustomer, String publicKeyBroker, String balanceType, String transactionType, double amount, String cashCurrencyType, String cashReference, long runningBookBalance, long runningAvailableBalance, long timeStamp, String memo, String status) {
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


    @Override
    public UUID getCashTransactionId() {
        return null;
    }

    @Override
    public String getPublicKeyActorFrom() {
        return null;
    }

    @Override
    public String getPublicKeyActorTo() {
        return null;
    }

    @Override
    public CashTransactionStatus getStatus() {
        return null;
    }

    @Override
    public BalanceType getBalanceType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public CashCurrencyType getCashCurrencyType() {
        return null;
    }

    @Override
    public String getCashReference() {
        return null;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public long getRunningBookBalance() {
        return 0;
    }

    @Override
    public long getRunningAvailableBalance() {
        return 0;
    }

    @Override
    public String getMemo() {
        return null;
    }
}
