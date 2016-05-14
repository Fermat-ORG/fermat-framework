package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.JsonDateAdapter;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication</code> is
 * the implementation of the message<p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceMessage implements Serializable {

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
     * Represent the senderType
     */
    private transient PlatformComponentType senderType;

    /**
     * Represent the receiverType
     */
    private transient PlatformComponentType receiverType;

    /**
     * Represent the senderNsType
     */
    private transient NetworkServiceType senderNsType;

    /**
     * Represent the receiverNsType
     */
    private transient NetworkServiceType receiverNsType;

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
     * Represent the failCount
     */
    private transient int failCount = 0;

    /**
     * Represent the status
     */
    private transient FermatMessagesStatus fermatMessagesStatus;

    /**
     * Represent the signature
     */
    private String signature;

    /**
     * Represent the fermatMessageContentType
     */
    private FermatMessageContentType fermatMessageContentType;

    /**
     * Constructor
     */
    public NetworkServiceMessage() {
        super();
        this.id = UUID.randomUUID();
        this.failCount = new Integer(0);
    }

    /**
     * Constructor with parameters
     *
     * @param content
     * @param deliveryTimestamp
     * @param fermatMessageContentType
     * @param fermatMessagesStatus
     * @param receiver
     * @param sender
     * @param shippingTimestamp
     * @param signature
     */
    public NetworkServiceMessage(String content, Timestamp deliveryTimestamp, FermatMessageContentType fermatMessageContentType, FermatMessagesStatus fermatMessagesStatus, String receiver, String sender, Timestamp shippingTimestamp, String signature) {

        this.id = UUID.randomUUID();
        this.content = content;
        this.deliveryTimestamp = deliveryTimestamp;
        this.fermatMessageContentType = fermatMessageContentType;
        this.fermatMessagesStatus = fermatMessagesStatus;
        this.receiver = receiver;
        this.sender = sender;
        this.shippingTimestamp = shippingTimestamp;
        this.signature = signature;
        this.failCount = new Integer(0);
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getContent()
     */
    
    public String getContent(){
        return content;
    }

    /**
     * Set the content
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getDeliveryTimestamp()
     */
    
    public Timestamp getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    /**
     * Set the DeliveryTimestamp
     *
     * @param deliveryTimestamp
     */
    public void setDeliveryTimestamp(Timestamp deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getFermatMessageContentType()
     */
    
    public FermatMessageContentType getFermatMessageContentType() {
        return fermatMessageContentType;
    }

    /**
     * Set the FermatMessageContentType
     * @param fermatMessageContentType
     */
    public void setFermatMessageContentType(FermatMessageContentType fermatMessageContentType) {
        this.fermatMessageContentType = fermatMessageContentType;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getFermatMessagesStatus()
     */
    public FermatMessagesStatus getFermatMessagesStatus() {
        return fermatMessagesStatus;
    }

    /**
     * Set the FermatMessagesStatus
     * @param fermatMessagesStatus
     */
    public void setFermatMessagesStatus(FermatMessagesStatus fermatMessagesStatus) {
        this.fermatMessagesStatus = fermatMessagesStatus;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getId()
     */
    
    public UUID getId() {
        return id;
    }

    /**
     * Set the Id
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getReceiver()
     */
    
    public String getReceiver() {
        return receiver;
    }

    /**
     * Set the Receiver
     * @param receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getSender()
     */
    
    public String getSender() {
        return sender;
    }

    /**
     * Set the Sender
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getShippingTimestamp()
     */
    public Timestamp getShippingTimestamp() {
        return shippingTimestamp;
    }

    /**
     * Set the ShippingTimestamp
     * @param shippingTimestamp
     */
    public void setShippingTimestamp(Timestamp shippingTimestamp) {
        this.shippingTimestamp = shippingTimestamp;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getFailCount()
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * Set the failCount
     * @param failCount
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#getSignature()
     */
    
    public String getSignature() {
        return signature;
    }

    /**
     * Set the signature
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#toJson()
     */
    
    public String toJson() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new JsonDateAdapter()).create();
        return gson.toJson(this);
    }

    /**
     * (no-javadoc)
     * @see FermatMessage#fromJson(String)
     */
    
    public NetworkServiceMessage fromJson(String json) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new JsonDateAdapter()).create();
        return gson.fromJson(json, NetworkServiceMessage.class);
    }

    /**
     * (no-javadoc)
     * @see Object#equals(Object)
     */
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FermatMessageCommunication)) return false;
        FermatMessageCommunication that = (FermatMessageCommunication) o;
        return Objects.equals(getId(), that.getId());
    }

    /**
     * (no-javadoc)
     * @see Object#hashCode()
     */
    
    public int hashCode() {
        return Objects.hash(getId(), getSender(), getReceiver(), getContent(), getShippingTimestamp(), getDeliveryTimestamp(), getFermatMessagesStatus(), getSignature(), getFermatMessageContentType());
    }

    
    public PlatformComponentType getSenderType() {
        return senderType;
    }

    public void setSenderType(PlatformComponentType senderType) {
        this.senderType = senderType;
    }

    
    public PlatformComponentType getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(PlatformComponentType receiverType) {
        this.receiverType = receiverType;
    }

    public NetworkServiceType getSenderNsType() {
        return senderNsType;
    }

    public void setSenderNsType(NetworkServiceType senderNsType) {
        this.senderNsType = senderNsType;
    }

    public NetworkServiceType getReceiverNsType() {
        return receiverNsType;
    }

    public void setReceiverNsType(NetworkServiceType receiverNsType) {
        this.receiverNsType = receiverNsType;
    }

    /**
     * (no-javadoc)
     * @see Object#toString()
     */
    
    public String toString() {
        return "FermatMessageCommunication{" +
                "content=" + content +
                ", id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", shippingTimestamp=" + shippingTimestamp +
                ", deliveryTimestamp=" + deliveryTimestamp +
                ", fermatMessagesStatus=" + fermatMessagesStatus +
                ", signature='" + signature + '\'' +
                ", failCount='" + failCount + '\'' +
                ", fermatMessageContentType=" + fermatMessageContentType +
                ", senderType=" + senderType +
                ", receiverType=" + receiverType +
                ", senderNsType=" + senderNsType +
                ", receiverNsType=" + receiverNsType +
                '}';
    }
}
