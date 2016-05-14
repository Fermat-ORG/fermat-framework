package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public final class IssuingRecord {

    //VARIABLE DECLARATION
    private int assetsCompleted;
    private int assetsToGenerate;
    private int assetsProcessed;
    private BlockchainNetworkType networkType;
    private DigitalAsset asset;
    private IssuingStatus status;
    private String btcWalletPk;
    private String issuerWalletPk;
    private Date creationTime;

    //CONSTRUCTORS


    public IssuingRecord(int assetsCompleted, int assetsToGenerate, int assetsProcessed, BlockchainNetworkType networkType, DigitalAsset asset, IssuingStatus status, String btcWalletPk, String issuerWalletPk, long creationTime) {
        this.assetsCompleted = assetsCompleted;
        this.assetsToGenerate = assetsToGenerate;
        this.assetsProcessed = assetsProcessed;
        this.networkType = networkType;
        this.asset = asset;
        this.status = status;
        this.btcWalletPk = btcWalletPk;
        this.issuerWalletPk = issuerWalletPk;
        this.creationTime = new Date(creationTime);
    }

    public IssuingRecord() {
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public int getAssetsCompleted() {
        return assetsCompleted;
    }

    public void setAssetsCompleted(int assetsCompleted) {
        this.assetsCompleted = assetsCompleted;
    }

    public int getAssetsToGenerate() {
        return assetsToGenerate;
    }

    public void setAssetsToGenerate(int assetsToGenerate) {
        this.assetsToGenerate = assetsToGenerate;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }

    public DigitalAsset getAsset() {
        return asset;
    }

    public void setAsset(DigitalAsset asset) {
        this.asset = asset;
    }

    public IssuingStatus getStatus() {
        return status;
    }

    public void setStatus(IssuingStatus status) {
        this.status = status;
    }

    public String getBtcWalletPk() {
        return btcWalletPk;
    }

    public void setBtcWalletPk(String btcWalletPk) {
        this.btcWalletPk = btcWalletPk;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getIssuerWalletPk() {
        return issuerWalletPk;
    }

    public void setIssuerWalletPk(String issuerWalletPk) {
        this.issuerWalletPk = issuerWalletPk;
    }

    public int getAssetsProcessed() {
        return assetsProcessed;
    }

    public void setAssetsProcessed(int assetsProcessed) {
        this.assetsProcessed = assetsProcessed;
    }

    //INNER CLASSES
}
