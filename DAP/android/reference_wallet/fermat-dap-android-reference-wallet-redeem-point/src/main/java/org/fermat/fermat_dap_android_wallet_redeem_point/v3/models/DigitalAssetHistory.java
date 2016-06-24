package org.fermat.fermat_dap_android_wallet_redeem_point.v3.models;


import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.sql.Timestamp;

/**
 * Created by Penny on 19/04/16.
 */
public class DigitalAssetHistory implements Comparable<DigitalAssetHistory> {

    private String stadicticID;
    private String historyNameAsset;
    private String historyNameUser;
    private Timestamp expDate;
    private Timestamp acceptedDate;
    private String walletPublicKey;
    private String assetPublicKey;
    private String actorUserPublicKey;
    private byte[] imageAsset;
    private byte[] imageActorUserFrom;

    public DigitalAssetHistory(String historyNameAsset, String historyNameUser) {
        this.historyNameAsset = historyNameAsset;
        this.historyNameUser = historyNameUser;
    }

    public DigitalAssetHistory() {
    }

    public String getHistoryNameAsset() {
        return historyNameAsset;
    }

    public void setHistoryNameAsset(String historyNameAsset) {
        this.historyNameAsset = historyNameAsset;
    }

    public String getHistoryNameUser() {
        return historyNameUser;
    }

    public void setHistoryNameUser(String historyNameUser) {
        this.historyNameUser = historyNameUser;
    }

    public Timestamp getExpDate() {
        return expDate;
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }

    public Timestamp getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Timestamp acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public String getActorUserPublicKey() {
        return actorUserPublicKey;
    }

    public void setActorUserPublicKey(String actorUserPublicKey) {
        this.actorUserPublicKey = actorUserPublicKey;
    }

    public byte[] getImageAsset() {
        return imageAsset;
    }

    public void setImageAsset(byte[] imageAsset) {
        this.imageAsset = imageAsset;
    }

    public byte[] getImageActorUserFrom() {
        return imageActorUserFrom;
    }

    public void setImageActorUserFrom(byte[] imageActorUserFrom) {
        this.imageActorUserFrom = imageActorUserFrom;
    }

    public String getFormattedExpDate() {
        if (expDate == null) return "No expiration date";
        return DAPStandardFormats.DATE_FORMAT.format(expDate);
    }

    public String getFormattedAcceptedDate() {
        if (acceptedDate == null) return "No accepted date";
        return DAPStandardFormats.DATE_FORMAT.format(acceptedDate);
    }

    public String getStadicticID() {
        return stadicticID;
    }

    public void setStadicticID(String stadicticID) {
        this.stadicticID = stadicticID;
    }

    @Override
    public int compareTo(DigitalAssetHistory another) {
        return another.getAcceptedDate().compareTo(this.getAcceptedDate());
    }
}
