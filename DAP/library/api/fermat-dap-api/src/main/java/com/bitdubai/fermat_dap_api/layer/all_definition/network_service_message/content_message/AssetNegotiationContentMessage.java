package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class AssetNegotiationContentMessage implements DAPContentMessage {


    //VARIABLE DECLARATION
    /**
     * The current status of the transaction on the moment this message was sent.
     */
    private AssetSellStatus sellStatus;
    /**
     * The AssetNegotiation object that will give us all the information relative to the
     * selling transaction.
     */
    private AssetNegotiation assetNegotiation;

    //CONSTRUCTORS


    public AssetNegotiationContentMessage(AssetSellStatus sellStatus, AssetNegotiation assetNegotiation) {
        this.sellStatus = sellStatus;
        this.assetNegotiation = assetNegotiation;
    }

    public AssetNegotiationContentMessage() {
    }

    //PUBLIC METHODS

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_NEGOTIATION;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    public AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(AssetSellStatus sellStatus) {
        this.sellStatus = sellStatus;
    }

    public AssetNegotiation getAssetNegotiation() {
        return assetNegotiation;
    }

    public void setAssetNegotiation(AssetNegotiation assetNegotiation) {
        this.assetNegotiation = assetNegotiation;
    }

    //INNER CLASSES
}
