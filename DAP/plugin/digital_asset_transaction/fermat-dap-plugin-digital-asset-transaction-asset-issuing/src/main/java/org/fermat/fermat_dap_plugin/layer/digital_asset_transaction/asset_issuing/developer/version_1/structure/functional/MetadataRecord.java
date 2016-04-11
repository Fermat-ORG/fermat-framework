package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public final class MetadataRecord {

    //VARIABLE DECLARATION
    private final UUID transactionId;
    private final DigitalAssetMetadata assetMetadata;
    private final IssuingStatus status;
    private final UUID outgoingId;

    //CONSTRUCTORS
    public MetadataRecord(UUID outgoingId, IssuingStatus status, DigitalAssetMetadata assetMetadata, UUID transactionId) {
        this.outgoingId = outgoingId;
        this.status = status;
        this.assetMetadata = assetMetadata;
        this.transactionId = transactionId;
    }


    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public UUID getTransactionId() {
        return transactionId;
    }

    public DigitalAssetMetadata getAssetMetadata() {
        return assetMetadata;
    }

    public UUID getOutgoingId() {
        return outgoingId;
    }

    public IssuingStatus getStatus() {
        return status;
    }

    //INNER CLASSES
}
