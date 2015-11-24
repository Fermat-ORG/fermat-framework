package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;

import java.util.UUID;

/**
 * Created by franklin on 01/10/15.
 */
public class AssetIssuerWalletTransactionRecordWrapper implements AssetIssuerWalletTransactionRecord {
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
    private final String memo;
    private final String digitalAssetMetadataHash;
    private final String genesisBlock;
    private final String transactionId;
    private final String genesisTransaction;
    private DigitalAssetMetadata digitalAssetMetadata;

    public AssetIssuerWalletTransactionRecordWrapper(DigitalAsset digitalAsset,
                                              String assetIssuingPublicKey,
                                              String name,
                                              String description,
                                              CryptoAddress addressFrom,
                                              CryptoAddress addressTo,
                                              String actorFromPublicKey,
                                              String actorToPublicKey,
                                              Actors actorFromType,
                                              Actors actorToType,
                                              long amount,
                                              long timeStamp,
                                              String memo,
                                              String digitalAssetMetadataHash,
                                                     String genesisBlock,
                                              String transactionId,
                                                     String genesisTransaction){
        this.digitalAsset = digitalAsset;
        this.assetIssuingPublicKey = assetIssuingPublicKey;
        this.name = name;
        this.description = description;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey =actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.memo = memo;
        this.digitalAssetMetadataHash = digitalAssetMetadataHash;
        this.genesisBlock = genesisBlock;
        this.transactionId = transactionId;
        this.genesisTransaction = genesisTransaction;

        this.digitalAssetMetadata = new DigitalAssetMetadata(this.digitalAsset);
        digitalAssetMetadata.setGenesisBlock(genesisBlock);
        digitalAssetMetadata.setGenesisTransaction(this.genesisTransaction);
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
        return digitalAssetMetadataHash;
    }

    @Override
    public String getGenesisBlock() {
        return this.genesisBlock;
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata() {
        return this.digitalAssetMetadata;
    }
}
