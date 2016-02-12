package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;

import java.sql.Timestamp;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/11/16.
 */
public class Transaction {
    private byte[] imagePerson;
    private String userName;
    private String transactionUserName;
    private Timestamp date;

    private AssetUserWalletTransaction assetUserWalletTransaction;

    public byte[] getImagePerson() {
        return imagePerson;
    }

    public void setImagePerson(byte[] imagePerson) {
        this.imagePerson = imagePerson;
    }

    private double amount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTransactionUserName() {
        return transactionUserName;
    }

    public void setTransactionUserName(String transactionUserName) {
        this.transactionUserName = transactionUserName;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getFormattedDate() {
        if (date == null) return "No transaction date";
        return DAPStandardFormats.DATE_FORMAT.format(date);
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AssetUserWalletTransaction getAssetUserWalletTransaction() {
        return assetUserWalletTransaction;
    }

    public void setAssetUserWalletTransaction(AssetUserWalletTransaction assetUserWalletTransaction) {
        this.assetUserWalletTransaction = assetUserWalletTransaction;
    }
}
