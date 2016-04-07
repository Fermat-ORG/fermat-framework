package org.fermat.fermat_dap_api.layer.dap_transaction.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetRedeemPointWalletTransactionRecordWrapper implements AssetRedeemPointWalletTransactionRecord {

    //VARIABLE DECLARATION
    private final org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset;
    private final String name;
    private final String description;
    private final CryptoAddress addressFrom;
    private final CryptoAddress addressTo;
    private final String actorFromPublicKey;
    private final String actorToPublicKey;
    private final Actors actorFromType;
    private final Actors actorToType;
    private final long amount;
    private final long timeStamp;

    {
        this.timeStamp = System.currentTimeMillis();
    }

    private String memo;

    {
        DateFormat sdf = DAPStandardFormats.DATETIME_FORMAT;
        this.memo = "Digital Asset Redeemed at:  " + sdf.format(new Date(timeStamp));
    }

    private final String transactionId;
    private final DigitalAssetMetadata digitalAssetMetadata;

    //CONSTRUCTORS

    public AssetRedeemPointWalletTransactionRecordWrapper(org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction assetMetadataTransaction,
                                                          CryptoAddress addressFrom,
                                                          CryptoAddress addressTo) {
        org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset = assetMetadataTransaction.getDigitalAssetMetadata().getDigitalAsset();
        this.digitalAsset = asset;
        this.name = asset.getName();
        this.description = asset.getDescription();
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = assetMetadataTransaction.getSenderId();
        this.actorToPublicKey = assetMetadataTransaction.getReceiverId();
        this.transactionId = assetMetadataTransaction.getGenesisTransaction();
        this.actorFromType = Actors.DAP_ASSET_USER;
        this.actorToType = Actors.DAP_ASSET_REDEEM_POINT;
        this.amount = asset.getGenesisAmount();
        this.digitalAssetMetadata = assetMetadataTransaction.getDigitalAssetMetadata();
    }


    public AssetRedeemPointWalletTransactionRecordWrapper(DigitalAssetMetadata assetMetadata,
                                                          String genesisTransaction,
                                                          String actorFromPublicKey,
                                                          String actorToPublicKey,
                                                          CryptoAddress addressFrom,
                                                          CryptoAddress addressTo) {
        org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset = assetMetadata.getDigitalAsset();
        this.digitalAsset = asset;
        this.name = asset.getName();
        this.description = asset.getDescription();
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.transactionId = genesisTransaction;
        this.actorFromType = Actors.DAP_ASSET_USER;
        this.actorToType = Actors.DAP_ASSET_REDEEM_POINT;
        this.amount = asset.getGenesisAmount();
        this.digitalAssetMetadata = assetMetadata;
    }


    public AssetRedeemPointWalletTransactionRecordWrapper(DigitalAssetMetadata assetMetadata,
                                                          CryptoTransaction cryptoTransaction,
                                                          String actorFromPublicKey,
                                                          String actorToPublicKey,
                                                          String memo) {
        org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset = assetMetadata.getDigitalAsset();
        this.digitalAsset = asset;
        this.name = asset.getName();
        this.description = asset.getDescription();
        this.addressFrom = cryptoTransaction.getAddressFrom();
        this.addressTo = cryptoTransaction.getAddressTo();
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.transactionId = cryptoTransaction.getTransactionHash();
        this.actorFromType = Actors.DAP_ASSET_USER;
        this.actorToType = Actors.DAP_ASSET_REDEEM_POINT;
        this.amount = cryptoTransaction.getCryptoAmount() != 0 ? cryptoTransaction.getCryptoAmount() : assetMetadata.getDigitalAsset().getGenesisAmount();
        this.digitalAssetMetadata = assetMetadata;
        this.memo = memo;
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    @Override
    public String getDigitalAssetPublicKey() {
        return digitalAsset.getPublicKey();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public String getIdTransaction() {
        return transactionId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public String getDigitalAssetMetadataHash() {
        return digitalAssetMetadata.getDigitalAssetHash();
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata() {
        return digitalAssetMetadata;
    }
    //INNER CLASSES
}
