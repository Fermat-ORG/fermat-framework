package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/11/15.
 */
public class DAPMessage implements Serializable {

    //VARIABLE DECLARATION
    private DAPMessageType messageType;
    private DAPContentMessage messageContent;

    //CONSTRUCTORS

    public DAPMessage() {
    }

    public DAPMessage(DAPMessageType messageType, DAPContentMessage messageContent) throws CantSetObjectException {
        setMessageType(messageType);
        setMessageContent(messageContent);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public DAPMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(DAPMessageType messageType) throws CantSetObjectException {
        this.messageType = Validate.verifySetter(messageType, "messageType is null");
    }

    public Object getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(DAPContentMessage messageContent) throws CantSetObjectException {
        this.messageContent = Validate.verifySetter(messageContent, "messageContent is null");
    }

    //INNER CLASSES
}
