package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.enums.ActorProtocolState;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public class NegotiationTransmissionImpl implements NegotiationTransmission {

    private final UUID transmissionId;
    private final UUID transactionId;
    private final UUID negotiationId;
    private NegotiationTransactionType negotiationTransactionType;
    private final String publicKeyActorSend;
    private final PlatformComponentType actorSendType;
    private final String publicKeyActorReceive;
    private final PlatformComponentType actorReceiveType;
    private NegotiationTransmissionType transmissionType;
    private NegotiationTransmissionState transmissionState;
    private NegotiationType negotiationType;
    private final String negotiationXML;
    private final long timestamp;
    private boolean pendingFlag;
    private boolean flagRead;
    private int sentCount;

    private UUID responseToNotificationId;

    //OLD ONE
    public NegotiationTransmissionImpl(
            final UUID transmissionId,
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationTransactionType negotiationTransactionType,
            final String publicKeyActorSend,
            final PlatformComponentType actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionType transmissionType,
            final NegotiationTransmissionState transmissionState,
            final NegotiationType negotiationType,
            final String negotiationXML,
            final long timestamp
    ) {
        this.transmissionId = transmissionId;
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationTransactionType = negotiationTransactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionType = transmissionType;
        this.transmissionState = transmissionState;
        this.negotiationType = negotiationType;
        this.negotiationXML = negotiationXML;
        this.timestamp = timestamp;
        this.pendingFlag = false;
    }

    public NegotiationTransmissionImpl(
            final UUID transmissionId,
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationTransactionType negotiationTransactionType,
            final String publicKeyActorSend,
            final PlatformComponentType actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionType transmissionType,
            final NegotiationTransmissionState transmissionState,
            final NegotiationType negotiationType,
            final String negotiationXML,
            final long timestamp,
            final boolean pendingFlag,
            final boolean flagRead,
            final ActorProtocolState actorProtocolState,
            final int sentCount,
            final UUID responseToNotificationId
    ) {
        this.transmissionId = transmissionId;
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationTransactionType = negotiationTransactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionType = transmissionType;
        this.transmissionState = transmissionState;
        this.negotiationType = negotiationType;
        this.negotiationXML = negotiationXML;
        this.timestamp = timestamp;
        this.pendingFlag = pendingFlag;
        this.flagRead = flagRead;
        this.sentCount = sentCount;
        this.responseToNotificationId = responseToNotificationId;
    }

    private NegotiationTransmissionImpl(JsonObject jsonObject, Gson gson) {

        this.transmissionId = UUID.fromString(jsonObject.get("transmissionId").getAsString());
        this.transactionId = UUID.fromString(jsonObject.get("transactionId").getAsString());
        this.negotiationId = UUID.fromString(jsonObject.get("negotiationId").getAsString());
        this.negotiationTransactionType = gson.fromJson(jsonObject.get("negotiationTransactionType").getAsString(), NegotiationTransactionType.class);
        this.publicKeyActorSend = (jsonObject.get("publicKeyActorSend") != null) ? jsonObject.get("publicKeyActorSend").getAsString() : null;
        this.actorSendType = gson.fromJson(jsonObject.get("actorSendType").getAsString(), PlatformComponentType.class);
        this.publicKeyActorReceive = jsonObject.get("publicKeyActorReceive").getAsString();
        this.actorReceiveType = gson.fromJson(jsonObject.get("actorReceiveType").getAsString(), PlatformComponentType.class);
        this.transmissionType = gson.fromJson(jsonObject.get("transmissionType").getAsString(), NegotiationTransmissionType.class);
        this.transmissionState = gson.fromJson(jsonObject.get("transmissionState").getAsString(), NegotiationTransmissionState.class);
        this.negotiationType = gson.fromJson(jsonObject.get("negotiationType").getAsString(), NegotiationType.class);
        this.negotiationXML = (jsonObject.get("negotiationXML") != null) ? jsonObject.get("negotiationXML").getAsString() : null;
        this.timestamp = jsonObject.get("timestamp").getAsLong();
        this.pendingFlag = jsonObject.get("pendingFlag").getAsBoolean();
        this.flagRead = jsonObject.get("flagRead").getAsBoolean();
        this.sentCount = jsonObject.get("sentCount").getAsInt();
//            this.responseToNotificationId = UUID.fromString(jsonObject.get("responseToNotificationId").getAsString());
//        System.out.println("12345 responseToNotificationId");

    }

    @Override
    public UUID getTransmissionId() {
        return transmissionId;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public NegotiationTransactionType getNegotiationTransactionType() {
        return negotiationTransactionType;
    }

    @Override
    public String getPublicKeyActorSend() {
        return publicKeyActorSend;
    }

    @Override
    public PlatformComponentType getActorSendType() {
        return actorSendType;
    }

    @Override
    public String getPublicKeyActorReceive() {
        return publicKeyActorReceive;
    }

    @Override
    public PlatformComponentType getActorReceiveType() {
        return actorReceiveType;
    }

    @Override
    public NegotiationTransmissionType getTransmissionType() {
        return transmissionType;
    }

    @Override
    public NegotiationTransmissionState getTransmissionState() {
        return transmissionState;
    }

    @Override
    public NegotiationType getNegotiationType() {
        return negotiationType;
    }

    @Override
    public void setNegotiationType(NegotiationType negotiationType) {
        this.negotiationType = negotiationType;
    }

    @Override
    public String getNegotiationXML() {
        return negotiationXML;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean isPendingToRead() {
        return this.pendingFlag;
    }

    @Override
    public void confirmRead() {
        this.pendingFlag = true;
    }

    @Override
    public void setNegotiationTransactionType(NegotiationTransactionType type) {
        this.negotiationTransactionType = type;
    }

    @Override
    public void setTransmissionType(NegotiationTransmissionType type) {
        this.transmissionType = type;
    }

    @Override
    public void setTransmissionState(NegotiationTransmissionState state) {
        this.transmissionState = state;
    }

    public boolean isFlagRead() {
        return flagRead;
    }

    public void setFlagRead(boolean flagRead) {
        this.flagRead = flagRead;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public UUID getResponseToNotificationId() {
        return responseToNotificationId;
    }

    public void setResponseToNotificationId(UUID responseToNotificationId) {
        this.responseToNotificationId = responseToNotificationId;
    }

    public boolean isPendingFlag() {
        return pendingFlag;
    }

    public void setPendingFlag(boolean pendingFlag) {
        this.pendingFlag = pendingFlag;
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("transmissionId", transmissionId.toString());
        jsonObject.addProperty("transactionId", transactionId.toString());
        jsonObject.addProperty("negotiationId", negotiationId.toString());
        jsonObject.addProperty("negotiationTransactionType", negotiationTransactionType.toString());
        jsonObject.addProperty("publicKeyActorSend", publicKeyActorSend);
        jsonObject.addProperty("actorSendType", actorSendType.toString());
        jsonObject.addProperty("publicKeyActorReceive", publicKeyActorReceive);
        jsonObject.addProperty("actorReceiveType", actorReceiveType.toString());
        jsonObject.addProperty("transmissionType", transmissionType.toString());
        jsonObject.addProperty("transmissionState", transmissionState.toString());
        jsonObject.addProperty("negotiationType", negotiationType.toString());
        jsonObject.addProperty("negotiationXML", negotiationXML);
        jsonObject.addProperty("timestamp", timestamp);
        jsonObject.addProperty("pendingFlag", pendingFlag);
        jsonObject.addProperty("flagRead", flagRead);
        jsonObject.addProperty("sentCount", sentCount);
        if (responseToNotificationId != null)
            jsonObject.addProperty("responseToNotificationId", responseToNotificationId.toString());
        return gson.toJson(jsonObject);

    }

    public static NegotiationTransmissionImpl fronJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new NegotiationTransmissionImpl(jsonObject, gson);
    }
}
