package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class AssetNegotiationContentMessage implements DAPContentMessage {


    //VARIABLE DECLARATION
    /**
     * The current status of the transaction on the moment this message was sent.
     */
    private org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus sellStatus;
    /**
     * The AssetNegotiation object that will give us all the information relative to the
     * selling transaction.
     */
    private org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation assetNegotiation;

    //CONSTRUCTORS


    public AssetNegotiationContentMessage(org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus sellStatus, org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation assetNegotiation) {
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

    public org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus sellStatus) {
        this.sellStatus = sellStatus;
    }

    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation getAssetNegotiation() {
        return assetNegotiation;
    }

    public void setAssetNegotiation(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation assetNegotiation) {
        this.assetNegotiation = assetNegotiation;
    }

    //INNER CLASSES
}
