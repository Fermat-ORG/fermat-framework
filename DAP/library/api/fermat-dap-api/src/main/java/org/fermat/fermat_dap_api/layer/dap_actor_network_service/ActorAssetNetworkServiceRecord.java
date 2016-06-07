package org.fermat.fermat_dap_api.layer.dap_actor_network_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * Created by Nerio on 11/02/16.
 */
public class ActorAssetNetworkServiceRecord implements org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification {
    private UUID id;
    private Actors actorDestinationType;
    private Actors actorSenderType;
    private String actorSenderPublicKey;
    private String actorDestinationPublicKey;
    private String actorSenderAlias;
    private byte[] actorSenderProfileImage;
    private org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor assetNotificationDescriptor;
    private long sentDate;
    private org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState actorAssetProtocolState;
    private boolean flagRead;
    private int sentCount;

    private String messageXML;
    private BlockchainNetworkType blockchainNetworkType;
    private UUID responseToNotificationId;

    public ActorAssetNetworkServiceRecord(UUID id,
                                          String actorSenderAlias,
//                                         String actorSenderPhrase,
                                          byte[] actorSenderProfileImage,
                                          org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor assetNotificationDescriptor,
                                          Actors actorDestinationType,
                                          Actors actorSenderType,
                                          String actorSenderPublicKey,
                                          String actorDestinationPublicKey,
                                          long sentDate,
                                          org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState actorAssetProtocolState,
                                          boolean flagRead,
                                          int sendCount,
                                          BlockchainNetworkType blockchainNetworkType,
                                          UUID responseToNotificationId,
                                          String messageXML) {

        this.id = id;
        this.actorSenderAlias = actorSenderAlias;
        this.actorSenderProfileImage = actorSenderProfileImage;
        this.assetNotificationDescriptor = assetNotificationDescriptor;
        this.actorDestinationType = actorDestinationType;
        this.actorSenderType = actorSenderType;
        this.actorSenderPublicKey = actorSenderPublicKey;
        this.actorDestinationPublicKey = actorDestinationPublicKey;
        this.sentDate = sentDate;
        this.actorAssetProtocolState = actorAssetProtocolState;
        this.flagRead = flagRead;
        this.sentCount = sendCount;
        if (blockchainNetworkType != null)
            this.blockchainNetworkType = blockchainNetworkType;
        this.responseToNotificationId = responseToNotificationId;
        this.messageXML = messageXML;
    }


    private ActorAssetNetworkServiceRecord(JsonObject jsonObject, Gson gson) {

        this.id = UUID.fromString(jsonObject.get("id").getAsString());
        this.actorSenderAlias = (jsonObject.get("actorSenderAlias") != null) ? jsonObject.get("actorSenderAlias").getAsString() : null;
        this.actorSenderProfileImage = Base64.decode(jsonObject.get("actorSenderProfileImage").getAsString(), Base64.DEFAULT);
        this.assetNotificationDescriptor = gson.fromJson(jsonObject.get("assetNotificationDescriptor").getAsString(), org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor.class);
        this.actorDestinationType = gson.fromJson(jsonObject.get("actorDestinationType").getAsString(), Actors.class);
        this.actorSenderType = gson.fromJson(jsonObject.get("actorSenderType").getAsString(), Actors.class);
        this.actorSenderPublicKey = jsonObject.get("actorSenderPublicKey").getAsString();
        this.actorDestinationPublicKey = jsonObject.get("actorDestinationPublicKey").getAsString();
        this.sentDate = jsonObject.get("sentDate").getAsLong();
        this.actorAssetProtocolState = gson.fromJson(jsonObject.get("actorAssetProtocolState").getAsString(), org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState.class);
        this.flagRead = jsonObject.get("flagRead").getAsBoolean();
        this.sentCount = jsonObject.get("sentCount").getAsInt();

        if (jsonObject.get("messageXML") != null)
            this.messageXML = jsonObject.get("messageXML").getAsString();
        if (jsonObject.get("blockchainNetworkType") != null)
            this.blockchainNetworkType = gson.fromJson(jsonObject.get("blockchainNetworkType").getAsString(), BlockchainNetworkType.class);
        if (jsonObject.get("responseToNotificationId") != null)
            this.responseToNotificationId = UUID.fromString(jsonObject.get("responseToNotificationId").getAsString());

    }

    @Override
    public String getActorSenderAlias() {
        return actorSenderAlias;
    }

    @Override
    public byte[] getActorSenderProfileImage() {
        return (actorSenderProfileImage != null) ? this.actorSenderProfileImage.clone() : null;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Actors getActorDestinationType() {
        return actorDestinationType;
    }

    @Override
    public String getActorDestinationPublicKey() {
        return actorDestinationPublicKey;
    }

    @Override
    public String getActorSenderPublicKey() {
        return actorSenderPublicKey;
    }

    @Override
    public Actors getActorSenderType() {
        return actorSenderType;
    }

    @Override
    public org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor getAssetNotificationDescriptor() {
        return assetNotificationDescriptor;
    }

    @Override
    public long getSentDate() {
        return sentDate;
    }

    public org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState getActorAssetProtocolState() {
        return actorAssetProtocolState;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public int getSentCount() {
        return sentCount;
    }

    public String getMessageXML() {
        return messageXML;
    }

    public void changeDescriptor(org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor assetNotificationDescriptor) {
        this.assetNotificationDescriptor = assetNotificationDescriptor;
    }

    public void changeState(org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState actorAssetProtocolState) {
        this.actorAssetProtocolState = actorAssetProtocolState;
    }

    public boolean isFlagRead() {
        return flagRead;
    }

    public void setFlagRead(boolean flagRead) {
        this.flagRead = flagRead;
    }

    public void setActorSenderPublicKey(String actorSenderPublicKey) {
        this.actorSenderPublicKey = actorSenderPublicKey;
    }

    public void setActorDestinationPublicKey(String actorDestinationPublicKey) {
        this.actorDestinationPublicKey = actorDestinationPublicKey;
    }

    public void setActorDestinationType(Actors actorDestinationType) {
        this.actorDestinationType = actorDestinationType;
    }

    public void setActorSenderType(Actors actorSenderType) {
        this.actorSenderType = actorSenderType;
    }

    public void setActorSenderAlias(String actorSenderAlias) {
        this.actorSenderAlias = actorSenderAlias;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public void setActorAssetProtocolState(org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState actorAssetProtocolState) {
        this.actorAssetProtocolState = actorAssetProtocolState;
    }

    public UUID getResponseToNotificationId() {
        return responseToNotificationId;
    }

    public void setResponseToNotificationId(UUID responseToNotificationId) {
        this.responseToNotificationId = responseToNotificationId;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public void setMessageXML(String messageXML) {
        this.messageXML = messageXML;
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id.toString());
        jsonObject.addProperty("actorDestinationType", actorDestinationType.toString());
        jsonObject.addProperty("actorSenderType", actorSenderType.toString());
        jsonObject.addProperty("actorSenderPublicKey", actorSenderPublicKey);
        jsonObject.addProperty("actorDestinationPublicKey", actorDestinationPublicKey);
        jsonObject.addProperty("actorSenderAlias", actorSenderAlias);
        jsonObject.addProperty("actorSenderProfileImage", Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT));
        jsonObject.addProperty("assetNotificationDescriptor", assetNotificationDescriptor.toString());
        jsonObject.addProperty("sentDate", sentDate);
        jsonObject.addProperty("actorAssetProtocolState", actorAssetProtocolState.toString());
        jsonObject.addProperty("flagRead", flagRead);
        jsonObject.addProperty("sentCount", sentCount);
        if (messageXML != null)
            jsonObject.addProperty("messageXML", messageXML);
        if (blockchainNetworkType != null)
            jsonObject.addProperty("blockchainNetworkType", blockchainNetworkType.toString());
        if (responseToNotificationId != null)
            jsonObject.addProperty("responseToNotificationId", responseToNotificationId.toString());
        return gson.toJson(jsonObject);

    }


    public static ActorAssetNetworkServiceRecord fronJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new ActorAssetNetworkServiceRecord(jsonObject, gson);
    }

    @Override
    public String toString() {
        String profileImageUser = null;
        if (actorSenderProfileImage != null)
            profileImageUser = Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT);

        return "AssetUserNetworkServiceRecord{" +
                "id=" + id +
                ", actorDestinationType=" + actorDestinationType +
                ", actorSenderType=" + actorSenderType +
                ", actorSenderPublicKey='" + actorSenderPublicKey + '\'' +
                ", actorDestinationPublicKey='" + actorDestinationPublicKey + '\'' +
                ", actorSenderAlias='" + actorSenderAlias + '\'' +
                ", actorSenderProfileImage=" + profileImageUser +
                ", assetNotificationDescriptor=" + assetNotificationDescriptor +
                ", sentDate=" + sentDate +
                ", actorAssetProtocolState=" + actorAssetProtocolState +
                ", flagRead=" + flagRead +
                ", sentCount=" + sentCount +
                ", messageXML=" + messageXML +
                ", blockchainNetworkType=" + blockchainNetworkType +
                ", responseToNotificationId=" + responseToNotificationId +
                '}';
    }
}