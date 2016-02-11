package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public final class SellingRecord {

    //VARIABLE DECLARATION
    private UUID recordId;
    private DigitalAssetMetadata metadata;
    private ActorAssetUser buyer;
    private AssetSellStatus status;
    private DraftTransaction draftTransaction;

    //CONSTRUCTORS

    public SellingRecord(UUID recordId, DigitalAssetMetadata metadata, ActorAssetUser buyer, AssetSellStatus status, DraftTransaction draftTransaction) {
        this.recordId = recordId;
        this.metadata = metadata;
        this.buyer = buyer;
        this.status = status;
        this.draftTransaction = draftTransaction;
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

    public ActorAssetUser getBuyer() {
        return buyer;
    }

    public AssetSellStatus getStatus() {
        return status;
    }

    public DraftTransaction getDraftTransaction() {
        return draftTransaction;
    }
    //INNER CLASSES
}
