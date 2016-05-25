package org.fermat.fermat_dap_api.layer.dap_transaction.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/10/15.
 */
public class AssetUserWalletTransactionRecordWrapper implements AssetUserWalletTransactionRecord {
    private final DigitalAsset digitalAsset;
    private final String digitalAssetPublicKey;
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

    private String memo;

    {
        this.memo = "Digital Asset delivered at" + this.timeStamp;
    }

    private final String transactionId;
    private final DigitalAssetMetadata digitalAssetMetadata;

    public AssetUserWalletTransactionRecordWrapper(DigitalAssetMetadata digitalAssetMetadata,
                                                   CryptoTransaction cryptoGenesisTransaction,
                                                   String actorFromPublicKey,
                                                   String actorToPublicKey) {
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.digitalAssetPublicKey = this.digitalAsset.getPublicKey();
        this.name = this.digitalAsset.getName();
        this.description = this.digitalAsset.getDescription();
        this.addressFrom = cryptoGenesisTransaction.getAddressFrom();
        this.addressTo = cryptoGenesisTransaction.getAddressTo();
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = Actors.INTRA_USER;
        this.actorToType = Actors.DAP_ASSET_ISSUER;
        this.amount = cryptoGenesisTransaction.getCryptoAmount() != 0 ? cryptoGenesisTransaction.getCryptoAmount() : digitalAssetMetadata.getDigitalAsset().getGenesisAmount();
        this.transactionId = cryptoGenesisTransaction.getTransactionHash();
        this.digitalAssetMetadata = digitalAssetMetadata;
    }

    public AssetUserWalletTransactionRecordWrapper(DigitalAssetMetadata digitalAssetMetadata,
                                                   CryptoTransaction cryptoGenesisTransaction,
                                                   String actorFromPublicKey,
                                                   Actors actorFromType,
                                                   String actorToPublicKey,
                                                   Actors actorToType) {
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.digitalAssetPublicKey = this.digitalAsset.getPublicKey();
        this.name = this.digitalAsset.getName();
        this.description = this.digitalAsset.getDescription();
        this.addressFrom = cryptoGenesisTransaction.getAddressFrom();
        this.addressTo = cryptoGenesisTransaction.getAddressTo();
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.amount = cryptoGenesisTransaction.getCryptoAmount() != 0 ? cryptoGenesisTransaction.getCryptoAmount() : digitalAssetMetadata.getDigitalAsset().getGenesisAmount();
        this.transactionId = cryptoGenesisTransaction.getTransactionHash();
        this.digitalAssetMetadata = digitalAssetMetadata;
    }

    public AssetUserWalletTransactionRecordWrapper(DigitalAssetMetadata digitalAssetMetadata,
                                                   CryptoTransaction cryptoGenesisTransaction,
                                                   String actorFromPublicKey,
                                                   Actors actorFromType,
                                                   String actorToPublicKey,
                                                   Actors actorToType,
                                                   String memo) {
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.digitalAssetPublicKey = this.digitalAsset.getPublicKey();
        this.name = this.digitalAsset.getName();
        this.description = this.digitalAsset.getDescription();
        this.addressFrom = cryptoGenesisTransaction.getAddressFrom();
        this.addressTo = cryptoGenesisTransaction.getAddressTo();
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.amount = cryptoGenesisTransaction.getCryptoAmount() != 0  ? cryptoGenesisTransaction.getCryptoAmount() : digitalAssetMetadata.getDigitalAsset().getGenesisAmount();
        this.transactionId = cryptoGenesisTransaction.getTransactionHash();
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.memo = memo;
    }

    public AssetUserWalletTransactionRecordWrapper(DigitalAssetMetadata digitalAssetMetadata,
                                                   CryptoTransaction cryptoGenesisTransaction,
                                                   DAPActor actorFrom,
                                                   DAPActor actorTo,
                                                   String memo) {
        this.digitalAsset = digitalAssetMetadata.getDigitalAsset();
        this.digitalAssetPublicKey = this.digitalAsset.getPublicKey();
        this.name = this.digitalAsset.getName();
        this.description = this.digitalAsset.getDescription();
        this.addressFrom = cryptoGenesisTransaction.getAddressFrom();
        this.addressTo = cryptoGenesisTransaction.getAddressTo();
        this.actorFromPublicKey = actorFrom.getActorPublicKey();
        this.actorToPublicKey = actorTo.getActorPublicKey();
        this.actorFromType = ActorUtils.getActorType(actorFrom);
        this.actorToType = ActorUtils.getActorType(actorTo);
        this.amount = cryptoGenesisTransaction.getCryptoAmount() != 0  ? cryptoGenesisTransaction.getCryptoAmount() : digitalAssetMetadata.getDigitalAsset().getGenesisAmount();
        this.transactionId = cryptoGenesisTransaction.getTransactionHash();
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.memo = memo;
    }

    @Override
    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    @Override
    public String getAssetIssuingPublicKey() {
        return digitalAssetPublicKey;
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
    public String getGenesisTransaction() {
        return digitalAssetMetadata.getGenesisTransaction();
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata() {
        return digitalAssetMetadata;
    }
}
