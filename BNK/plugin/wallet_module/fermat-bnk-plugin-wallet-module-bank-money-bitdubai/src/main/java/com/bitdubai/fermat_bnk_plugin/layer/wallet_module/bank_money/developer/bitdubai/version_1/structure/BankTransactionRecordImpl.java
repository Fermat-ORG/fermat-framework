package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by guillermo on 03/02/16.
 */
public class BankTransactionRecordImpl implements BankMoneyTransactionRecord, Serializable {

    private float amount;
    private String memo;
    private long timestamp;
    private TransactionType transactionType;
    private BankTransactionStatus bankTransactionStatus;

    public BankTransactionRecordImpl(float amount, String memo, long timestamp,TransactionType transactionType,BankTransactionStatus bankTransactionStatus) {
        this.amount = amount;
        this.memo = memo;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.bankTransactionStatus = bankTransactionStatus;
    }

    @Override
    public UUID getBankTransactionId() {
        return null;
    }

    @Override
    public BankTransactionStatus getStatus() {
        return bankTransactionStatus;
    }

    @Override
    public BalanceType getBalanceType() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public FiatCurrency getCurrencyType() {
        return null;
    }

    @Override
    public BankOperationType getBankOperationType() {
        return null;
    }

    @Override
    public String getBankDocumentReference() {
        return null;
    }

    @Override
    public String getBankName() {
        return null;
    }

    @Override
    public String getBankAccountNumber() {
        return null;
    }

    @Override
    public BankAccountType getBankAccountType() {
        return null;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
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
        return this.memo;
    }
}
