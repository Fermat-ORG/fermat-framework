package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetMovementContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private DAPActor systemUser;
    private DAPActor newUser;
    private String assetPublicKey;
    private BlockchainNetworkType networkType;
    private org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType movementType;

    //CONSTRUCTORS
    public AssetMovementContentMessage() {
    }

    public AssetMovementContentMessage(DAPActor systemUser, DAPActor newUser, String assetPublicKey, BlockchainNetworkType networkType, org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType movementType) {
        this.systemUser = systemUser;
        this.newUser = newUser;
        this.assetPublicKey = assetPublicKey;
        this.networkType = networkType;
        this.movementType = movementType;
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
    public DAPActor getNewUser() {
        return newUser;
    }

    public void setNewUser(DAPActor newUser) {
        this.newUser = newUser;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }

    public DAPActor getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(DAPActor systemUser) {
        this.systemUser = systemUser;
    }

    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType movementType) {
        this.movementType = movementType;
    }

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_MOVEMENT;
    }
    //INNER CLASSES
}
