package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class AssetSellContentMessage implements DAPContentMessage {
    //VARIABLE DECLARATION
    private UUID sellingId;
    private byte[] serializedTransaction;
    private AssetSellStatus sellStatus;
    private DigitalAssetMetadata assetMetadata;
    private UUID negotiationId;
    //CONSTRUCTORS

    public AssetSellContentMessage() {
    }

    public AssetSellContentMessage(UUID sellingId, byte[] serializedTransaction, AssetSellStatus sellStatus, DigitalAssetMetadata assetMetadata, UUID negotiationId) {
        this.sellingId = sellingId;
        this.serializedTransaction = serializedTransaction;
        this.sellStatus = sellStatus;
        this.assetMetadata = assetMetadata;
        this.negotiationId = negotiationId;
    }

    //PUBLIC METHODS

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_SELL;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public byte[] getSerializedTransaction() {
        return serializedTransaction;
    }

    public AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public UUID getSellingId() {
        return sellingId;
    }

    public DigitalAssetMetadata getAssetMetadata() {
        return assetMetadata;
    }

    public UUID getNegotiationId() {
        return negotiationId;
    }

    //INNER CLASSES
}
