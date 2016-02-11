package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellStatusUpdateContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private AssetSellStatus sellStatus;

    //CONSTRUCTORS
    public AssetSellStatusUpdateContentMessage(AssetSellStatus sellStatus) {
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
        return DAPMessageType.ASSET_SELL_STATUS_UPDATE;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS
    public AssetSellStatus getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(AssetSellStatus sellStatus) {
        this.sellStatus = sellStatus;
    }

    //INNER CLASSES
}
