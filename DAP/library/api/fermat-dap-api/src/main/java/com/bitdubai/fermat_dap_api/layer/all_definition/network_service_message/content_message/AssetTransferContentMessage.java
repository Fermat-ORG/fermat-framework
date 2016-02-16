package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetTransferContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private DAPActor actor;
    private UUID metadataId;
    private BlockchainNetworkType networkType;

    //CONSTRUCTORS
    public AssetTransferContentMessage() {
    }

    public AssetTransferContentMessage(DAPActor actor, UUID metadataId, BlockchainNetworkType networkType) {
        this.actor = actor;
        this.metadataId = metadataId;
        this.networkType = networkType;
    }

    //PUBLIC METHODS

    /**
     * This method will be use to give a XML Representation of this object. Which can be parsed
     * backwards to get this object.
     *
     * @return {@link String} with this object in XML.
     */
    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
    public DAPActor getActor() {
        return actor;
    }

    public void setActor(DAPActor actor) {
        this.actor = actor;
    }

    public UUID getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(UUID metadataId) {
        this.metadataId = metadataId;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_TRANSFER;
    }
    //INNER CLASSES
}
