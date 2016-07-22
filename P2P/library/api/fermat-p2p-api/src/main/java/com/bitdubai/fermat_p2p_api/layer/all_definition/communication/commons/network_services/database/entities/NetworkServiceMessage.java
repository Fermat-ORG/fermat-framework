package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.TimestampAdapter;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage</code> is
 * the implementation of the message<p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceMessage extends PackageContent implements AbstractBaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private           UUID                     id                     ;
    private           String                   content                ;
    private transient NetworkServiceType       networkServiceType     ;
    private           String                   senderPublicKey        ;
    private           String                   receiverPublicKey      ;

    private           Timestamp                shippingTimestamp      ;
    private transient Timestamp                deliveryTimestamp      ;

    private transient Boolean                  isBetweenActors        ;
    private transient FermatMessagesStatus     fermatMessagesStatus   ;

    private           String                   signature              ;

    private transient int                      failCount              = 0;

    public NetworkServiceMessage() {

        this.id = UUID.randomUUID();
        this.failCount = 0;
        this.isBetweenActors = Boolean.FALSE;
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

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
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

    public Boolean isBetweenActors() {
        return isBetweenActors;
    }

    public void setIsBetweenActors(Boolean isBetweenActors) {
        this.isBetweenActors = isBetweenActors;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampAdapter()).create();
        return gson.toJson(this);
    }

    public static NetworkServiceMessage parseContent(String content) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampAdapter()).create();
        return gson.fromJson(content, NetworkServiceMessage.class);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkServiceMessage)) return false;
        return this.toJson().equals(((NetworkServiceMessage) o).toJson());
//        NetworkServiceMessage that = (NetworkServiceMessage) o;
//        return Objects.equals(getId(), that.getId());
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
                ", receiverPublicKey='" + receiverPublicKey + '\'' +
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
