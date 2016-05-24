package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

import java.util.UUID;

/**
 * This POJO was created to be used as a message content for pass information
 * relative to asset appropriation through network services;
 * <p/>
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/11/15.
 */
public class AssetAppropriationContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION

    private UUID transactionId;
    private String userThatAppropriate;
    private BlockchainNetworkType networkType;

    //CONSTRUCTORS

    public AssetAppropriationContentMessage() {
    }

    public AssetAppropriationContentMessage(UUID transactionId, String userThatAppropriate, BlockchainNetworkType networkType) {
        this.transactionId = transactionId;
        this.userThatAppropriate = userThatAppropriate;
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

    public String getUserThatAppropriate() {
        return userThatAppropriate;
    }

    public void setUserThatAppropriate(String userThatAppropriate) {
        this.userThatAppropriate = userThatAppropriate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
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
        return DAPMessageType.ASSET_APPROPRIATION;
    }

    //INNER CLASSES
}
