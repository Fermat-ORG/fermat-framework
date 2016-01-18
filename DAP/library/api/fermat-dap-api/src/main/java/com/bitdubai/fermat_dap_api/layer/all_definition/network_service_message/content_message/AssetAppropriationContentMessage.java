package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import java.io.Serializable;

/**
 * This POJO was created to be used as a message content for pass information
 * relative to asset appropriation through network services;
 * <p/>
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/11/15.
 */
public class AssetAppropriationContentMessage implements Serializable, DAPContentMessage {

    //VARIABLE DECLARATION

    private String digitalAssetAppropriated;
    private String userThatAppropriate;

    //CONSTRUCTORS

    public AssetAppropriationContentMessage() {
    }

    public AssetAppropriationContentMessage(String digitalAssetAppropriated, String userThatAppropriate) {
        this.digitalAssetAppropriated = digitalAssetAppropriated;
        this.userThatAppropriate = userThatAppropriate;
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

    public String getDigitalAssetAppropriated() {
        return digitalAssetAppropriated;
    }

    public void setDigitalAssetAppropriated(String digitalAssetAppropriated) {
        this.digitalAssetAppropriated = digitalAssetAppropriated;
    }

    //INNER CLASSES
}
