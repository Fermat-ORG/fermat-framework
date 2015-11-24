package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;

import java.util.UUID;

/**
 * Created by francisco on 05/11/15.
 */
public class TransactionBankMoney implements BankMoneyTransactionRecord {

    UUID bankTransactionId;
    String publicKeyBroker;
    String publicKeyCustomer;
    String balanceType;
    String transactionType;
    double amount;
    String cashCurrencyType;
    String bankOperationType;
    String bankDocumentReference;
    String bankName;
    String bankNumber;
    String bankAccountType;
    long runningBookBalance;
    long runningAvailableBalance;
    long timeStamp;
    String memo;
    String status;

    public TransactionBankMoney(UUID bankTransactionId, String publicKeyBroker, String publicKeyCustomer, String balanceType, String transactionType, double amount, String cashCurrencyType, String bankOperationType, String bankDocumentReference, String bankName, String bankNumber, String bankAccountType, long runningBookBalance, long runningAvailableBalance, long timeStamp, String memo, String status) {
        this.bankTransactionId = bankTransactionId;
        this.publicKeyBroker = publicKeyBroker;
        this.publicKeyCustomer = publicKeyCustomer;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashCurrencyType = cashCurrencyType;
        this.bankOperationType = bankOperationType;
        this.bankDocumentReference = bankDocumentReference;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.bankAccountType = bankAccountType;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.status = status;
    }

    @Override
    public UUID getBankTransactionId() {
        return null;
    }

    @Override
    public BankTransactionStatus getStatus() {
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
    public String getPublicKeyActorFrom() {
        return null;
    }

    @Override
    public String getPublicKeyActorTo() {
        return null;
    }

    @Override
    public float getAmount() {
        return 0;
    }

    @Override
    public BankCurrencyType getBankCurrencyType() {
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
