/*
 * @#Packet.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.google.gson.Gson;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CommunicationFermatPacket</code> implements
 * the package to transport the data
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 11/06/15.
 *
 * @version 1.0
 */
public class CommunicationFermatPacket implements FermatPacket {

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
     * Represent the fermatMessage
     */
	private FermatMessage fermatMessage;

    /**
     * Represent the signature
     */
	private String signature;

    /**
     * Represent the networkServices
     */
    private NetworkServiceType networkServiceType;

    /**
     * Constructor
     */
    public CommunicationFermatPacket() {
        this.id = UUID.randomUUID();
        this.destination = null;
        this.sender = null;
        this.fermatPacketType = null;
        this.fermatMessage = null;
        this.signature = null;
        this.networkServiceType = null;
    }

    /**
     * Constructor with parameters
     *
     * @param destination
     * @param sender
     * @param fermatPacketType
     * @param fermatMessage
     * @param signature
     * @param networkServiceType
     */
    public CommunicationFermatPacket(String destination, String sender, FermatPacketType fermatPacketType, FermatMessage fermatMessage, String signature, NetworkServiceType networkServiceType) {
        this.id = UUID.randomUUID();
        this.destination = destination;
        this.sender = sender;
        this.fermatPacketType = fermatPacketType;
        this.fermatMessage = fermatMessage;
        this.signature = signature;
        this.networkServiceType = networkServiceType;
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
     * @see FermatPacket#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Set the  Network Service Type
     *
     * @param networkServicesType
     */
    public void setNetworkServiceType(NetworkServiceType networkServicesType) {
        this.networkServiceType = networkServicesType;
    }

    /**
     * (non-Javadoc)
     * @see FermatPacket#getFermatMessage()
     */
    @Override
    public FermatMessage getFermatMessage() {
        return fermatMessage;
    }

    /**
     * Set the fermatMessage of the packet
     *
     * @param fermatMessage
     */
    public void setFermatMessage(FermatMessage fermatMessage) {
        this.fermatMessage = fermatMessage;
    }

    /**
     * Convert this object to json string
     *
     * @return String json
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
        return gson.fromJson(json, CommunicationFermatPacket.class);

    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */



    /**
     * (non-Javadoc)
     * @see Object#hashCode()
     */



    /**
     * (non-Javadoc)
     * @see Object#toString()
     */


}
