/*
 * @#FermatPacketCommunication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.FermatPacketCommunication</code> implements
 * the package to transport the data
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 */
public class FermatPacketCommunication implements FermatPacket, Serializable {

    /**
     * Represent the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represent the id
     */
    private UUID id;

    /**
     * Represent the sender
     */
	private String sender;

    /**
     * Represent the destination
     */
	private String destination;

    /**
     * Represent the fermatPacketType
     */
	private FermatPacketType fermatPacketType;

    /**
     * Represent the messageContent
     */
	private String messageContent;

    /**
     * Represent the signature
     */
	private String signature;

    /**
     * Constructor
     */
    public FermatPacketCommunication() {
        this.id = UUID.randomUUID();
        this.destination = null;
        this.sender = null;
        this.fermatPacketType = null;
        this.messageContent = null;
        this.signature = null;
    }

    /**
     * Constructor with parameters
     *
     * @param destination
     * @param sender
     * @param fermatPacketType
     * @param messageContent
     * @param signature
     */
    public FermatPacketCommunication(String destination, String sender, FermatPacketType fermatPacketType, String messageContent, String signature) {
        this.id = UUID.randomUUID();
        this.destination = destination;
        this.sender = sender;
        this.fermatPacketType = fermatPacketType;
        this.messageContent = messageContent;
        this.signature = signature;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getId()
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getDestination()
     */
    @Override
    public String getDestination() {
        return destination;
    }

    /**
     * Set the destination of the packet
     *
     * @param destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return fermatPacketType;
    }

    /**
     * Set the fermatPacketType of the packet
     *
     * @param type
     */
    public void setFermatPacketType(FermatPacketType type) {
        this.fermatPacketType = type;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getSignature()
     */
    @Override
    public String getSignature() {
        return signature;
    }

    /**
     * Set the signature of the packet
     *
     * @return String
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getSender()
     */
    @Override
    public String getSender() {
        return sender;
    }

    /**
     * Set the sender of the packet
     *
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getMessageContent()
     */
    @Override
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Set the message Content of the packet
     *
     * @param messageContent
     */
    public void setFermatMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#toJson()
     */
    public String toJson(){

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#fromJson(String)
     */
    @Override
    public FermatPacket fromJson(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, FermatPacketCommunication.class);

    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FermatPacketCommunication)) return false;
        FermatPacketCommunication that = (FermatPacketCommunication) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getSender(), that.getSender()) &&
                Objects.equals(getDestination(), that.getDestination()) &&
                Objects.equals(getFermatPacketType(), that.getFermatPacketType()) &&
                Objects.equals(getMessageContent(), that.getMessageContent()) &&
                Objects.equals(getSignature(), that.getSignature());
    }

    /**
     * (non-Javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSender(), getDestination(), getFermatPacketType(), getMessageContent(), getSignature());
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "FermatPacketCommunication{" +
                "destination='" + destination + '\'' +
                ", id=" + id +
                ", sender='" + sender + '\'' +
                ", fermatPacketType=" + fermatPacketType +
                ", messageContent=" + messageContent +
                ", signature='" + signature +
                '}';
    }
}
