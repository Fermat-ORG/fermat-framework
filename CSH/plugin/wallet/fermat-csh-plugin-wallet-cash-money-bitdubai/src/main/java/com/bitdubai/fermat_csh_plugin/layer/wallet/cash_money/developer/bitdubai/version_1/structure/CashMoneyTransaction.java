package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

/**
 * Created by francisco on 22/10/15.
 */
public class CashMoneyTransaction {
    String CashTransactionId;
    String PublicKeyActorFrom;
    String PublicKeyActorTo;
    String Status;
    String BalanceType;
    String TransactionType;
    String Amount;
    String CashCurrencyType;
    String CashReference;
    String Timestamp;
    String Memo;

    public CashMoneyTransaction(String cashTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String status, String balanceType, String transactionType, String amount, String cashCurrencyType, String cashReference, String timestamp, String memo) {
        CashTransactionId = cashTransactionId;
        PublicKeyActorFrom = publicKeyActorFrom;
        PublicKeyActorTo = publicKeyActorTo;
        Status = status;
        BalanceType = balanceType;
        TransactionType = transactionType;
        Amount = amount;
        CashCurrencyType = cashCurrencyType;
        CashReference = cashReference;
        Timestamp = timestamp;
        Memo = memo;
    }

    public String getCashTransactionId() {
        return CashTransactionId;
    }

    public String getPublicKeyActorFrom() {
        return PublicKeyActorFrom;
    }

    public String getPublicKeyActorTo() {
        return PublicKeyActorTo;
    }

    public String getStatus() {
        return Status;
    }

    public String getBalanceType() {
        return BalanceType;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getAmount() {
        return Amount;
    }

    public String getCashCurrencyType() {
        return CashCurrencyType;
    }

    public String getCashReference() {
        return CashReference;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public String getMemo() {
        return Memo;
    }
}
