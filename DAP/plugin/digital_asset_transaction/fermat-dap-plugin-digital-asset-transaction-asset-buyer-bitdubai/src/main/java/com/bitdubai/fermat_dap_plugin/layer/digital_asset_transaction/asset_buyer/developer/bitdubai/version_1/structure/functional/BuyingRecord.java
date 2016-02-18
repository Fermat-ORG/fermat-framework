package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public final class BuyingRecord {

    //VARIABLE DECLARATION
    private UUID recordId;
    private DigitalAssetMetadata metadata;
    private ActorAssetUser seller;
    private AssetSellStatus status;
    private DraftTransaction buyerTransaction;
    private DraftTransaction sellerTransaction;
    private String broadcastingTxHash;
    private UUID negotiationId;

    //CONSTRUCTORS

    public BuyingRecord(UUID recordId, DigitalAssetMetadata metadata, ActorAssetUser seller, AssetSellStatus status, DraftTransaction buyerTransaction, DraftTransaction sellerTransaction, String broadcastingTxHash, UUID negotiationId) {
        this.recordId = recordId;
        this.metadata = metadata;
        this.seller = seller;
        this.status = status;
        this.buyerTransaction = buyerTransaction;
        this.sellerTransaction = sellerTransaction;
        this.broadcastingTxHash = broadcastingTxHash;
        this.negotiationId = negotiationId;
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public UUID getRecordId() {
        return recordId;
    }

    public DigitalAssetMetadata getMetadata() {
        return metadata;
    }

    public ActorAssetUser getSeller() {
        return seller;
    }

    public AssetSellStatus getStatus() {
        return status;
    }

    public DraftTransaction getBuyerTransaction() {
        return buyerTransaction;
    }

    public DraftTransaction getSellerTransaction() {
        return sellerTransaction;
    }

    public String getBroadcastingTxHash() {
        return broadcastingTxHash;
    }

    public UUID getNegotiationId() {
        return negotiationId;
    }

    //INNER CLASSES
}
