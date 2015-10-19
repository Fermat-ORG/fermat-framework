package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

/**
 * Created by francisco on 15/10/15.
 */
public class CashMoney {

    private String getCashTransactionId;
    private String getPublicKeyActorFrom;
    private String getPublicKeyActorTo;
    private String getStatus;
    private String getBalanceType;
    private String getTransactionType;
    private String getAmount;
    private String getCashCurrencyType;
    private String getCashReference;
    private String getTimestamp;
    private String getMemo;

    public CashMoney() {

    }

    public String getGetCashTransactionId() {
        return getCashTransactionId;
    }

    public String getGetPublicKeyActorFrom() {
        return getPublicKeyActorFrom;
    }

    public String getGetPublicKeyActorTo() {
        return getPublicKeyActorTo;
    }

    public String getGetStatus() {
        return getStatus;
    }

    public String getGetBalanceType() {
        return getBalanceType;
    }

    public String getGetTransactionType() {
        return getTransactionType;
    }

    public String getGetAmount() {
        return getAmount;
    }

    public String getGetCashCurrencyType() {
        return getCashCurrencyType;
    }

    public String getGetCashReference() {
        return getCashReference;
    }

    public String getGetTimestamp() {
        return getTimestamp;
    }

    public String getGetMemo() {
        return getMemo;
    }

    public CashMoney(String getCashTransactionId, String getPublicKeyActorFrom, String getPublicKeyActorTo, String getStatus, String getBalanceType, String getTransactionType, String getAmount, String getCashCurrencyType, String getCashReference, String getTimestamp, String getMemo) {
        this.getCashTransactionId = getCashTransactionId;
        this.getPublicKeyActorFrom = getPublicKeyActorFrom;
        this.getPublicKeyActorTo = getPublicKeyActorTo;
        this.getStatus = getStatus;
        this.getBalanceType = getBalanceType;
        this.getTransactionType = getTransactionType;
        this.getAmount = getAmount;
        this.getCashCurrencyType = getCashCurrencyType;
        this.getCashReference = getCashReference;
        this.getTimestamp = getTimestamp;
        this.getMemo = getMemo;
    }

}
