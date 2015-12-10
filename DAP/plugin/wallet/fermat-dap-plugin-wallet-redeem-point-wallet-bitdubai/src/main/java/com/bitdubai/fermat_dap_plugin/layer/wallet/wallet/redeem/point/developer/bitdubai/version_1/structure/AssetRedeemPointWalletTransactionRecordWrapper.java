package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletTransactionRecordWrapper implements AssetRedeemPointWalletTransactionRecord {
    private final DigitalAsset digitalAsset;
    private final String assetIssuingPublicKey;
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
        timeStamp = System.currentTimeMillis();
    }

    private final String memo;

    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.memo = "Digital Asset Redeemed at:  " + sdf.format(new Date(timeStamp));
    }

    private final String transactionId;
    private final DigitalAssetMetadata digitalAssetMetadata;

    public AssetRedeemPointWalletTransactionRecordWrapper(String transactionId,
                                                          DigitalAssetMetadata digitalAssetMetadata,
                                                          CryptoAddress addressFrom,
                                                          CryptoAddress addressTo,
                                                          String actorFromPublicKey,
                                                          String actorToPublicKey,
                                                          Actors actorFromType,
                                                          Actors actorToType) {
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.assetIssuingPublicKey = digitalAsset.getIdentityAssetIssuer().getPublicKey();
        this.name = digitalAsset.getName();
        this.description = digitalAsset.getDescription();
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.amount = digitalAsset.getGenesisAmount();
        this.transactionId = transactionId;
        this.digitalAssetMetadata = digitalAssetMetadata;
    }

    @Override
    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    @Override
    public String getDigitalAssetPublicKey() {
        return assetIssuingPublicKey;
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
}
