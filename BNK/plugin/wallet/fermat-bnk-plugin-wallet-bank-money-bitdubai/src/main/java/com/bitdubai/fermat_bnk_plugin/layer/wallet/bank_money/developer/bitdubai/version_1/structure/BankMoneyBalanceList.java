package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyBalanceRecord;

import java.util.UUID;

/**
 * Created by francisco on 10/11/15.
 */
public class BankMoneyBalanceList implements BankMoneyWalletBalance {
    UUID bankTransactionId;
    String publicKeyActorFrom;
    String publicKeyActorTo;
    String balanceType;
    String transactionType;
    double  amount;
    String bankCurrencyType;
    String bankOperationType;
    String bankDocumentReference;
    String bankName;
    String bankAccountNumber;
    String bankAccountType;
    long runningBookBalance;
    long runningAvailableBalance;
    long timestamp;
    String getMemo;

    public UUID getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(UUID bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public String getPublicKeyActorFrom() {
        return publicKeyActorFrom;
    }

    public void setPublicKeyActorFrom(String publicKeyActorFrom) {
        this.publicKeyActorFrom = publicKeyActorFrom;
    }

    public String getPublicKeyActorTo() {
        return publicKeyActorTo;
    }

    public void setPublicKeyActorTo(String publicKeyActorTo) {
        this.publicKeyActorTo = publicKeyActorTo;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBankCurrencyType() {
        return bankCurrencyType;
    }

    public void setBankCurrencyType(String bankCurrencyType) {
        this.bankCurrencyType = bankCurrencyType;
    }

    public String getBankOperationType() {
        return bankOperationType;
    }

    public void setBankOperationType(String bankOperationType) {
        this.bankOperationType = bankOperationType;
    }

    public String getBankDocumentReference() {
        return bankDocumentReference;
    }

    public void setBankDocumentReference(String bankDocumentReference) {
        this.bankDocumentReference = bankDocumentReference;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public long getRunningBookBalance() {
        return runningBookBalance;
    }

    public void setRunningBookBalance(long runningBookBalance) {
        this.runningBookBalance = runningBookBalance;
    }

    public long getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    public void setRunningAvailableBalance(long runningAvailableBalance) {
        this.runningAvailableBalance = runningAvailableBalance;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGetMemo() {
        return getMemo;
    }

    public void setGetMemo(String getMemo) {
        this.getMemo = getMemo;
    }

    public BankMoneyBalanceList(UUID bankTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String balanceType, String transactionType, double amount, String bankCurrencyType, String bankOperationType, String bankDocumentReference, String bankName, String bankAccountNumber, String bankAccountType, long runningBookBalance, long runningAvailableBalance, long timestamp, String getMemo) {
        this.bankTransactionId = bankTransactionId;
        this.publicKeyActorFrom = publicKeyActorFrom;
        this.publicKeyActorTo = publicKeyActorTo;
        this.balanceType = balanceType;
        this.transactionType = transactionType;
        this.amount = amount;
        this.bankCurrencyType = bankCurrencyType;
        this.bankOperationType = bankOperationType;
        this.bankDocumentReference = bankDocumentReference;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountType = bankAccountType;
        this.runningBookBalance = runningBookBalance;
        this.runningAvailableBalance = runningAvailableBalance;
        this.timestamp = timestamp;
        this.getMemo = getMemo;
    }

    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return 0;
    }

    @Override
    public void debit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {

    }

    @Override
    public void credit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {

    }
}
