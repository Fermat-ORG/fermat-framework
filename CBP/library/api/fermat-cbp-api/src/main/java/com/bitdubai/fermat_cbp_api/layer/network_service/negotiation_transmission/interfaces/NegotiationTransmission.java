package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public interface NegotiationTransmission {

    /**
     * The method <code>getTransmissionId</code> returns the transmission id of the negotiation transmission
     *
     * @return an UUID the transmission id of the negotiation transmission
     */
    UUID getTransmissionId();

    /**
     * The method <code>getTransactionId</code> returns the transaction id of the negotiation transmission
     *
     * @return an UUID the transaction id of the negotiation transmission
     */
    UUID getTransactionId();

    /**
     * The method <code>getNegotiationId</code> returns the negotiation id of the negotiation transmission
     *
     * @return an UUID the negotiation id of the negotiation
     */
    UUID getNegotiationId();

    /**
     * The method <code>getNegotiationTransactionType</code> returns the transaction type of the negotiation transmission
     *
     * @return an NegotiationTransactionType of the transaction type
     */
    NegotiationTransactionType getNegotiationTransactionType();

    /**
     * The method <code>getPublicKeyActorSend</code> returns the public key the actor send of the negotiation transaction
     *
     * @return an String the public key of the actor send
     */
    String getPublicKeyActorSend();

    /**
     * The method <code>getActorSendType</code> returns the actor send type of the negotiation transmission
     *
     * @return an PlatformComponentType of the actor send type
     */
    PlatformComponentType getActorSendType();

    /**
     * The method <code>getPublicKeyActorReceive</code> returns the public key the actor receive of the negotiation transmission
     *
     * @return an String the public key of the actor receive
     */
    String getPublicKeyActorReceive();

    /**
     * The method <code>getActorReceiveType</code> returns the actor receive type of the negotiation transmission
     *
     * @return an PlatformComponentType of the actor receive type
     */
    PlatformComponentType getActorReceiveType();

    /**
     * The method <code>getTransmissionType</code> returns the type of the negotiation transmission
     *
     * @return an NegotiationTransmissionType of the negotiation type
     */
    NegotiationTransmissionType getTransmissionType();

    /**
     * The method <code>getTransmissionState</code> returns the state of the negotiation transmission
     *
     * @return an NegotiationTransmissionStateof the negotiation state
     */
    NegotiationTransmissionState getTransmissionState();

    /**
     * The method <code>getNegotiationXML</code> returns the xml of the negotiation relationship with negotiation transmission
     *
     * @return an NegotiationType the negotiation type of negotiation
     */
    NegotiationType getNegotiationType();

    void setNegotiationType(NegotiationType negotiationType);

    /**
     * The method <code>getNegotiationXML</code> returns the xml of the negotiation relationship with negotiation transmission
     *
     * @return an String the xml of negotiation
     */
    String getNegotiationXML();

    /**
     * The method <code>getTimestamp</code> returns the time stamp of the negotiation transmission
     *
     * @return an Long the time stamp of the negotiation transmission
     */
    long getTimestamp();

    /**
     * The method <code>isPendingToRead</code> returns  if this pending to read the negotiation transmission
     *
     * @return an boolean if this pending to read
     */
    boolean isPendingToRead();

    /**
     * The method <code>confirmRead</code> confirm the read of the negotiation trasmission
     */
    void confirmRead();

    /**
     * The method <code>setNegotiationTransactionType</code> set the negotiation transaction type
     */
    void setNegotiationTransactionType(NegotiationTransactionType negotiationTransactionType);

    /**
     * The method <code>setTransmissionType</code> set the Transmission Type
     */
    void setTransmissionType(NegotiationTransmissionType negotiationTransmissionType);

    /**
     * The method <code>setTransmissionState</code> set the Transmission State
     */
    void setTransmissionState(NegotiationTransmissionState negotiationTransmissionState);

    public boolean isFlagRead();

    public void setFlagRead(boolean flagRead);

    public int getSentCount();

    public void setSentCount(int sentCount);

    public UUID getResponseToNotificationId();

    public void setResponseToNotificationId(UUID responseToNotificationId);

    public boolean isPendingFlag();

    public void setPendingFlag(boolean pendingFlag);

    public String toJson();

}
