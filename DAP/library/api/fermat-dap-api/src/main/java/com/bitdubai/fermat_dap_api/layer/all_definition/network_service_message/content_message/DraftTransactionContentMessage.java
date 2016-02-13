package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class DraftTransactionContentMessage implements DAPContentMessage {
    //VARIABLE DECLARATION
    private UUID sellingId;
    private byte[] serializedTransaction;
    private AssetSellStatus sellStatus;

    //CONSTRUCTORS
    public DraftTransactionContentMessage() {
    }

    public DraftTransactionContentMessage(UUID sellingId, byte[] serializedTransaction, AssetSellStatus sellStatus) {
        this.sellingId = sellingId;
        this.serializedTransaction = serializedTransaction;
        this.sellStatus = sellStatus;
    }

    //PUBLIC METHODS

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.DRAFT_TRANSACTION;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public byte[] getSerializedTransaction() {
        return serializedTransaction;
    }

    public void setSerializedTransaction(byte[] serializedTransaction) {
        this.serializedTransaction = serializedTransaction;
    }

    public AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(AssetSellStatus sellStatus) {
        this.sellStatus = sellStatus;
    }

    public UUID getSellingId() {
        return sellingId;
    }

    public void setSellingId(UUID sellingId) {
        this.sellingId = sellingId;
    }
    //INNER CLASSES
}
