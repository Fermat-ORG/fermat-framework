/*
 * @#Message.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Message</code> is
 * message representation
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Message implements Serializable{

    /**
     * Represent the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represent the id of the message
     */
    private UUID id;

    /**
     * Represent the sender of the message
     */
    private String sender;

    /**
     * Represent the receiver of the message
     */
    private String receiver;

    /**
     * Represent the content
     */
    private String content;

    /**
     * Represent the shipping timestamp of the message
     */
    private Timestamp shippingTimestamp;

    /**
     * Represent the delivery timestamp of the message
     */
    private Timestamp deliveryTimestamp;

    /**
     * Represent the signature
     */
    private String signature;

    /**
     * Represent the status
     */
    private MessageStatus messageStatus;

    /**
     * Represent the fermatMessageContentType
     */
    private MessageContentType messageContentType;

    /**
     * Constructor
     *
     * @param content
     * @param deliveryTimestamp
     * @param messageContentType
     * @param messageStatus
     * @param receiver
     * @param sender
     * @param shippingTimestamp
     * @param signature
     */
    public Message(String content, Timestamp deliveryTimestamp, MessageContentType messageContentType, MessageStatus messageStatus, String receiver, String sender, Timestamp shippingTimestamp, String signature) {
        this.content = content;
        this.deliveryTimestamp = deliveryTimestamp;
        this.messageContentType = messageContentType;
        this.messageStatus = messageStatus;
        this.id = UUID.randomUUID();
        this.receiver = receiver;
        this.sender = sender;
        this.shippingTimestamp = shippingTimestamp;
        this.signature = signature;
    }

    /**
     * Gets the value of content and returns
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content
     *
     * @param content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the value of deliveryTimestamp and returns
     *
     * @return deliveryTimestamp
     */
    public Timestamp getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    /**
     * Sets the deliveryTimestamp
     *
     * @param deliveryTimestamp to set
     */
    public void setDeliveryTimestamp(Timestamp deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    /**
     * Gets the value of id and returns
     *
     * @return id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the value of messageContentType and returns
     *
     * @return messageContentType
     */
    public com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType getMessageContentType() {
        return messageContentType;
    }

    /**
     * Sets the messageContentType
     *
     * @param messageContentType to set
     */
    public void setMessageContentType(com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType messageContentType) {
        this.messageContentType = messageContentType;
    }

    /**
     * Gets the value of messageStatus and returns
     *
     * @return messageStatus
     */
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    /**
     * Sets the messageStatus
     *
     * @param messageStatus to set
     */
    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * Gets the value of receiver and returns
     *
     * @return receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver
     *
     * @param receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Gets the value of sender and returns
     *
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender
     *
     * @param sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the value of serialVersionUID and returns
     *
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets the value of shippingTimestamp and returns
     *
     * @return shippingTimestamp
     */
    public Timestamp getShippingTimestamp() {
        return shippingTimestamp;
    }

    /**
     * Sets the shippingTimestamp
     *
     * @param shippingTimestamp to set
     */
    public void setShippingTimestamp(Timestamp shippingTimestamp) {
        this.shippingTimestamp = shippingTimestamp;
    }

    /**
     * Gets the value of signature and returns
     *
     * @return signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature
     *
     * @param signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", shippingTimestamp=" + shippingTimestamp +
                ", deliveryTimestamp=" + deliveryTimestamp +
                ", signature='" + signature + '\'' +
                ", messageStatus=" + messageStatus +
                ", messageContentType=" + messageContentType +
                '}';
    }
}
