package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/11/15.
 */
public class DAPMessage implements Serializable {

    //VARIABLE DECLARATION
    /**
     * The id of this message
     */
    private UUID idMessage;

    {
        idMessage = UUID.randomUUID();
    }

    /**
     * The content of this message, this one should wrap all
     * the needed information to process and answer this message
     * to continue the respective data flow.
     */
    private DAPContentMessage messageContent;
    /**
     * The actor who is sending this message.
     */
    private DAPActor actorSender;
    /**
     * The actor who will receive this message.
     */
    private DAPActor actorReceiver;
    /**
     * The subject from which this message is intending to be used for,
     * if the subject is not specified it will be consider as a default
     * message for that type of content, if there are more than one plugin
     * listening to that content without specifying the subject both will answer.
     */
    private DAPMessageSubject subject;

    {
        subject = DAPMessageSubject.DEFAULT;
    }

    //CONSTRUCTORS

    public DAPMessage() {
    }

    public DAPMessage(DAPContentMessage messageContent,
                      DAPActor actorSender,
                      DAPActor actorReceiver) throws CantSetObjectException {
        setMessageContent(messageContent);
        setActorSender(actorSender);
        setActorReceiver(actorReceiver);
    }

    public DAPMessage(DAPContentMessage messageContent,
                      DAPActor actorSender,
                      DAPActor actorReceiver,
                      DAPMessageSubject subject) throws CantSetObjectException {
        setMessageContent(messageContent);
        setActorSender(actorSender);
        setActorReceiver(actorReceiver);
        setSubject(subject);
    }

    public DAPMessage(UUID idMessage,
                      DAPContentMessage messageContent,
                      DAPActor actorSender,
                      DAPActor actorReceiver,
                      DAPMessageSubject subject) throws CantSetObjectException {
        setMessageContent(messageContent);
        setActorSender(actorSender);
        setActorReceiver(actorReceiver);
        setSubject(subject);
        setIdMessage(idMessage);
    }

    //PUBLIC METHODS

    public String messageAsXML() {
        return XMLParser.parseObject(this);
    }

    @Override
    public String toString() {
        return "DAPMessage{" +
                ", idMessage=" + idMessage +
                ", messageContent=" + messageContent +
                ", actorSender=" + actorSender + " - " + actorSender.getClass().getSimpleName() +
                ", actorReceiver=" + actorReceiver + " - " + actorReceiver.getClass().getSimpleName() +
                '}';
    }

    public String toJson() {
        return DAPMessageGson.getGson().toJson(this);
    }

    public static DAPMessage fromJson(String json) {
        return DAPMessageGson.getGson().fromJson(json, DAPMessage.class);
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
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

    public DAPMessageSubject getSubject() {
        return subject;
    }

    public void setSubject(DAPMessageSubject subject) throws CantSetObjectException {
        this.subject = Validate.verifySetter(subject, "subject is null");
    }

    public UUID getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(UUID idMessage) {
        this.idMessage = idMessage;
    }
    //INNER CLASSES
}
