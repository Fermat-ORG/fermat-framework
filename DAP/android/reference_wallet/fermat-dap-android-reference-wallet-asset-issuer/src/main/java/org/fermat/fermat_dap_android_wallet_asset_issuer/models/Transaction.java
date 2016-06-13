package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/11/16.
 */
public class Transaction implements Serializable {
    private byte[] actorImage;
    private String actorName;
    private TransactionType transactionType;
    private BalanceType balanceType;
    private Timestamp date;
    private double amount;
    private int quantity;
    private String memo;

    private AssetIssuerWalletTransaction assetIssuerWalletTransaction;

    public Transaction(AssetIssuerWalletTransaction assetIssuerWalletTransaction, DAPActor dapActor) {
        setAssetIssuerWalletTransaction(assetIssuerWalletTransaction);
        setActorName(dapActor.getName());
        setAmount(assetIssuerWalletTransaction.getAmount());
//        setQuantity(assetIssuerWalletTransaction.get);
        setDate(new Timestamp(assetIssuerWalletTransaction.getTimestamp()));
        setActorImage(dapActor.getProfileImage());
        setTransactionType(assetIssuerWalletTransaction.getTransactionType());
        setBalanceType(assetIssuerWalletTransaction.getBalanceType());
        setMemo(assetIssuerWalletTransaction.getMemo());
    }

    public Transaction() {
    }

    public byte[] getActorImage() {
        return actorImage;
    }

    public void setActorImage(byte[] actorImage) {
        this.actorImage = actorImage;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getFormattedDate() {
        if (date == null) return "No transaction date";
        return DAPStandardFormats.SIMPLE_DATETIME_FORMAT.format(date);
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public AssetIssuerWalletTransaction getAssetIssuerWalletTransaction() {
        return assetIssuerWalletTransaction;
    }

    public void setAssetIssuerWalletTransaction(AssetIssuerWalletTransaction assetIssuerWalletTransaction) {
        this.assetIssuerWalletTransaction = assetIssuerWalletTransaction;
    }
}
