package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.JsonDateAdapter;
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
public class NetworkServiceMessage extends PackageContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private           UUID                     id                     ;
    private           String                   content                ;
    private transient NetworkServiceType       networkServiceType     ;
    private           String                   senderPublicKey        ;
    private transient String                   senderClientPublicKey  ;
    private transient String                   senderActorType        ;
    private           String                   receiverPublicKey      ;
    private transient String                   receiverNsPublicKey    ;
    private transient String                   receiverClientPublicKey;
    private transient String                   receiverActorType      ;

    private           Timestamp                shippingTimestamp      ;
    private           Timestamp                deliveryTimestamp      ;

    private           Boolean                  isBetweenActors        ;
    private transient FermatMessagesStatus     fermatMessagesStatus   ;

    private           String                   signature              ;

    private transient int                      failCount              = 0;



    public NetworkServiceMessage() {

        this.id = UUID.randomUUID();
        this.failCount = 0;
        this.isBetweenActors = Boolean.FALSE;
    }

    public NetworkServiceMessage(final String                   content             ,
                                 final Timestamp                deliveryTimestamp   ,
                                 final FermatMessagesStatus     fermatMessagesStatus,
                                 final String                   receiverPublicKey   ,
                                 final String                   senderPublicKey     ,
                                 final Timestamp                shippingTimestamp   ,
                                 final String                   signature           ) {

        this.id                   = UUID.randomUUID()   ;
        this.content              = content             ;
        this.deliveryTimestamp    = deliveryTimestamp   ;
        this.fermatMessagesStatus = fermatMessagesStatus;
        this.receiverPublicKey    = receiverPublicKey   ;
        this.senderPublicKey      = senderPublicKey     ;
        this.shippingTimestamp    = shippingTimestamp   ;
        this.signature            = signature           ;
        this.failCount            = 0                   ;
        this.isBetweenActors      = Boolean.FALSE       ;
    }

    public NetworkServiceMessage(final String                   content                ,
                                 final NetworkServiceType       networkServiceType     ,
                                 final String                   senderPublicKey        ,
                                 final String                   senderClientPublicKey  ,
                                 final String                   senderActorType        ,
                                 final String                   receiverPublicKey      ,
                                 final String                   receiverClientPublicKey,
                                 final String                   receiverActorType      ,
                                 final Timestamp                shippingTimestamp      ,
                                 final Timestamp                deliveryTimestamp      ,
                                 final FermatMessagesStatus     fermatMessagesStatus   ,
                                 final String                   signature              ) {

        this.content                 = content                ;
        this.networkServiceType      = networkServiceType     ;
        this.senderPublicKey         = senderPublicKey        ;
        this.senderClientPublicKey   = senderClientPublicKey  ;
        this.senderActorType         = senderActorType        ;
        this.receiverPublicKey       = receiverPublicKey      ;
        this.receiverClientPublicKey = receiverClientPublicKey;
        this.receiverActorType       = receiverActorType      ;
        this.shippingTimestamp       = shippingTimestamp      ;
        this.deliveryTimestamp       = deliveryTimestamp      ;
        this.fermatMessagesStatus    = fermatMessagesStatus   ;
        this.signature               = signature              ;

        if (senderActorType != null)
            this.isBetweenActors     = Boolean.TRUE           ;
        else
            this.isBetweenActors     = Boolean.FALSE          ;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getSenderClientPublicKey() {
        return senderClientPublicKey;
    }

    public void setSenderClientPublicKey(String senderClientPublicKey) {
        this.senderClientPublicKey = senderClientPublicKey;
    }

    public String getSenderActorType() {
        return senderActorType;
    }

    public void setSenderActorType(String senderActorType) {
        this.senderActorType = senderActorType;
    }

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
    }

    public String getReceiverClientPublicKey() {
        return receiverClientPublicKey;
    }

    public void setReceiverClientPublicKey(String receiverClientPublicKey) {
        this.receiverClientPublicKey = receiverClientPublicKey;
    }

    public String getReceiverActorType() {
        return receiverActorType;
    }

    public void setReceiverActorType(String receiverActorType) {
        this.receiverActorType = receiverActorType;
    }

    public Timestamp getShippingTimestamp() {
        return shippingTimestamp;
    }

    public void setShippingTimestamp(Timestamp shippingTimestamp) {
        this.shippingTimestamp = shippingTimestamp;
    }

    public Timestamp getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public void setDeliveryTimestamp(Timestamp deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public FermatMessagesStatus getFermatMessagesStatus() {
        return fermatMessagesStatus;
    }

    public void setFermatMessagesStatus(FermatMessagesStatus fermatMessagesStatus) {
        this.fermatMessagesStatus = fermatMessagesStatus;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public String getReceiverNsPublicKey() {
        return receiverNsPublicKey;
    }

    public void setReceiverNsPublicKey(String receiverNsPublicKey) {
        this.receiverNsPublicKey = receiverNsPublicKey;
    }

    public Boolean isBetweenActors() {
        return isBetweenActors;
    }

    public void setIsBetweenActors(Boolean isBetweenActors) {
        this.isBetweenActors = isBetweenActors;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new JsonDateAdapter()).create();
        return gson.toJson(this);
    }

    public NetworkServiceMessage fromJson(String json) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new JsonDateAdapter()).create();
        return gson.fromJson(json, NetworkServiceMessage.class);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkServiceMessage)) return false;
        NetworkServiceMessage that = (NetworkServiceMessage) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "NetworkServiceMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", networkServiceType=" + networkServiceType +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                ", senderClientPublicKey='" + senderClientPublicKey + '\'' +
                ", senderActorType='" + senderActorType + '\'' +
                ", receiverPublicKey='" + receiverPublicKey + '\'' +
                ", receiverNsPublicKey='" + receiverNsPublicKey + '\'' +
                ", receiverClientPublicKey='" + receiverClientPublicKey + '\'' +
                ", receiverActorType='" + receiverActorType + '\'' +
                ", shippingTimestamp=" + shippingTimestamp +
                ", deliveryTimestamp=" + deliveryTimestamp +
                ", isBetweenActors=" + isBetweenActors +
                ", fermatMessagesStatus=" + fermatMessagesStatus +
                ", contentType=" + getMessageContentType() +
                ", signature='" + signature + '\'' +
                ", failCount=" + failCount +
                '}';
    }
}
