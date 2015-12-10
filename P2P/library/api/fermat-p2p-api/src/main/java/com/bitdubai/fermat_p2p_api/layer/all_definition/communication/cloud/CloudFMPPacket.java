/*
 * @#CloudFMPPacket.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.google.gson.Gson;

import java.util.Objects;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.FermatPacketCommunication</code> implements
 * the package to transport the data
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 11/06/15.
 *
 * @version 1.0
 */
public class CloudFMPPacket implements FMPPacket {

    /**
     * Represent the sender
     */
	private String sender;

    /**
     * Represent the destination
     */
	private String destination;

    /**
     * Represent the type
     */
	private FMPPacketType type;

    /**
     * Represent the message
     */
	private String message;

    /**
     * Represent the signature
     */
	private String signature;

    /**
     * Represent the networkServices
     */
    private NetworkServices networkServices;

    /**
     * Constructor
     */
    public CloudFMPPacket() {
        this.destination = null;
        this.sender = null;
        this.type = null;
        this.message = null;
        this.signature = null;
        this.networkServices = null;
    }

    /**
     * Constructor with parameters
     *
     * @param destination
     * @param sender
     * @param type
     * @param message
     * @param signature
     * @param networkServicesType
     */
    public CloudFMPPacket(String destination, String sender, FMPPacketType type, String message, String signature, NetworkServices networkServicesType) {
        this.destination = destination;
        this.sender = sender;
        this.type = type;
        this.message = message;
        this.signature = signature;
        this.networkServices = networkServicesType;
    }

    /**
     * (non-Javadoc)
     * @see FMPPacket#getDestination()
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
     * @see FMPPacket#getType()
     */
    @Override
    public FMPPacketType getType() {
        return type;
    }

    /**
     * Set the type of the packet
     *
     * @param type
     */
    public void setType(FMPPacketType type) {
        this.type = type;
    }

    /**
     * (non-Javadoc)
     * @see FMPPacket#getSignature()
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
     * @see FMPPacket#getSender()
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
     * @see FMPPacket#getNetworkServices()
     */
    @Override
    public NetworkServices getNetworkServices() {
        return networkServices;
    }

    /**
     * (non-Javadoc)
     * @see FMPPacket#setNetworkServices(NetworkServices)
     */
    @Override
    public void setNetworkServices(NetworkServices networkServices) {
        this.networkServices = networkServices;
    }

    /**
     * (non-Javadoc)
     * @see FMPPacket#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Set the message of the packet
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @see FMPPacket#fromJson(String)
     */
    @Override
    public FMPPacket fromJson(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, CloudFMPPacket.class);

    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CloudFMPPacket)) return false;
        CloudFMPPacket that = (CloudFMPPacket) o;
        return Objects.equals(getSender(), that.getSender()) &&
                Objects.equals(getDestination(), that.getDestination()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getSignature(), that.getSignature()) &&
                Objects.equals(getNetworkServices(), that.getNetworkServices());
    }

    /**
     * (non-Javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSender(), getDestination(), getType(), getMessage(), getSignature(), getNetworkServices());
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "FermatPacketCommunication{" +
                "destination='" + destination + '\'' +
                ", sender='" + sender + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", signature='" + signature + '\'' +
                ", networkServices=" + networkServices +
                '}';
    }
}
