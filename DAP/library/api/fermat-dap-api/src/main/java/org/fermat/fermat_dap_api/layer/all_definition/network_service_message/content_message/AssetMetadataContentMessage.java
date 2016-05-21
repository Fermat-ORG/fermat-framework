package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetMetadataContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private DigitalAssetMetadata assetMetadata;

    //CONSTRUCTORS

    public AssetMetadataContentMessage() {
    }

    public AssetMetadataContentMessage(DigitalAssetMetadata assetMetadata) {
        this.assetMetadata = assetMetadata;
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

    public DigitalAssetMetadata getAssetMetadata() {
        return assetMetadata;
    }

    public void setAssetMetadata(DigitalAssetMetadata assetMetadata) {
        this.assetMetadata = assetMetadata;
    }

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.ASSET_METADATA;
    }

    //INNER CLASSES
}
