package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/11/15.
 */
public class DAPMessage implements Serializable {

    //VARIABLE DECLARATION
    private DAPMessageType messageType;
    private DAPContentMessage messageContent;
    private DAPActor actorSender;
    private DAPActor actorReceiver;

    //CONSTRUCTORS

    public DAPMessage() {
    }

    public DAPMessage(DAPMessageType messageType,
                      DAPContentMessage messageContent,
                      DAPActor actorSender,
                      DAPActor actorReceiver) throws CantSetObjectException {
        setMessageType(messageType);
        setMessageContent(messageContent);
        setActorSender(actorSender);
        setActorReceiver(actorReceiver);
    }

    //PUBLIC METHODS

    public String messageAsXML() {
        return XMLParser.parseObject(this);
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public DAPMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(DAPMessageType messageType) throws CantSetObjectException {
        this.messageType = Validate.verifySetter(messageType, "messageType is null");
    }

    public DAPContentMessage getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(DAPContentMessage messageContent) throws CantSetObjectException {
        this.messageContent = Validate.verifySetter(messageContent, "messageContent is null");
    }

    public DAPActor getActorSender() {
        return actorSender;
    }

    public void setActorSender(DAPActor actorSender) throws CantSetObjectException {
        this.actorSender = Validate.verifySetter(actorSender, "actorSender is null");
    }

    public DAPActor getActorReceiver() {
        return actorReceiver;
    }

    public void setActorReceiver(DAPActor actorReceiver) throws CantSetObjectException {
        this.actorReceiver = Validate.verifySetter(actorReceiver, "actorReceiver is null");
    }

    //INNER CLASSES
}
