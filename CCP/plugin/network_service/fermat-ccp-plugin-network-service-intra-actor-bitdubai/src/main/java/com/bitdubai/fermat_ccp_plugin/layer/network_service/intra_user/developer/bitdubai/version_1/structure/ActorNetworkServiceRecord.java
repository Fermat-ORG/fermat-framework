package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;

import java.util.UUID;



/**
 * Created by Matias Furszyfer on 2015.10.15..
 */
public class ActorNetworkServiceRecord implements IntraUserNotification {

    private UUID id;
    private Actors actorDestinationType;
    private Actors actorSenderType;
    private String actorSenderPublicKey;
    private String actorDestinationPublicKey;
    private String actorSenderAlias;
    private String actorSenderPhrase;
    private byte[] actorSenderProfileImage;
    private NotificationDescriptor notificationDescriptor;
    private long sentDate;
    private ActorProtocolState actorProtocolState;
    private boolean flagReadead;
    private int sentCount;

    private UUID responseToNotificationId;



    public ActorNetworkServiceRecord(UUID id, String actorSenderAlias,String actorSenderPhrase, byte[] actorSenderProfileImage, NotificationDescriptor notificationDescriptor, Actors actorDestinationType, Actors actorSenderType, String actorSenderPublicKey, String actorDestinationPublicKey,long sentDate,ActorProtocolState actorProtocolState,boolean flagReadead, int sendCount,UUID responseToNotificationId) {
        this.id = id;
        this.actorSenderAlias = actorSenderAlias;
        this.actorSenderProfileImage = actorSenderProfileImage;
        this.notificationDescriptor = notificationDescriptor;
        this.actorDestinationType = actorDestinationType;
        this.actorSenderType = actorSenderType;
        this.actorSenderPublicKey = actorSenderPublicKey;
        this.actorDestinationPublicKey = actorDestinationPublicKey;
        this.sentDate = sentDate;
        this.actorProtocolState = actorProtocolState;
        this.flagReadead = flagReadead;
        this.sentCount = sendCount;
        this.actorSenderPhrase = actorSenderPhrase;
        this.responseToNotificationId = responseToNotificationId;
    }


    private ActorNetworkServiceRecord(JsonObject jsonObject, Gson gson) {

        this.id                        = UUID.fromString(jsonObject.get("id").getAsString());
        this.actorSenderAlias          = (jsonObject.get("actorSenderAlias")!=null)?jsonObject.get("actorSenderAlias").getAsString():null;
        this.actorSenderProfileImage   = Base64.decode(jsonObject.get("actorSenderProfileImage").getAsString(), Base64.DEFAULT);
        this.notificationDescriptor    = gson.fromJson(jsonObject.get("notificationDescriptor").getAsString(), NotificationDescriptor.class);
        this.actorDestinationType      = gson.fromJson(jsonObject.get("actorDestinationType").getAsString(), Actors.class);
        this.actorSenderType           = gson.fromJson(jsonObject.get("actorSenderType").getAsString(), Actors.class);
        this.actorSenderPublicKey      = jsonObject.get("actorSenderPublicKey").getAsString();
        this.actorDestinationPublicKey = jsonObject.get("actorDestinationPublicKey").getAsString();
        this.sentDate                  = jsonObject.get("sentDate").getAsLong();
        this.actorProtocolState        = gson.fromJson(jsonObject.get("actorProtocolState").getAsString(), ActorProtocolState.class);
        this.flagReadead               = jsonObject.get("flagReadead").getAsBoolean();
        this.sentCount                 = jsonObject.get("sentCount").getAsInt();
        this.actorSenderPhrase         = jsonObject.get("actorSenderPhrase").getAsString();
        if(jsonObject.get("responseToNotificationId")!=null)this.responseToNotificationId  = UUID.fromString(jsonObject.get("responseToNotificationId").getAsString());

    }



    @Override
    public String getActorSenderAlias() {
        return actorSenderAlias;
    }

    @Override
    public String getActorSenderPhrase() {
        return actorSenderPhrase;
    }

    @Override
    public byte[] getActorSenderProfileImage() {
        return (actorSenderProfileImage!=null) ? this.actorSenderProfileImage.clone() : null;
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
    public NotificationDescriptor getNotificationDescriptor() {
        return notificationDescriptor;
    }

    @Override
    public long getSentDate() {
        return sentDate;
    }

    public ActorProtocolState getActorProtocolState() {
        return actorProtocolState;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void changeDescriptor(NotificationDescriptor notificationDescriptor){
        this.notificationDescriptor = notificationDescriptor;
    }

    public void changeState(ActorProtocolState actorProtocolState){
        this.actorProtocolState = actorProtocolState;
    }

    public boolean isFlagReadead() {
        return flagReadead;
    }

    public void setFlagReadead(boolean flagReadead) {
        this.flagReadead = flagReadead;
    }

    public void setActorSenderPublicKey(String actorSenderPublicKey) {
        this.actorSenderPublicKey = actorSenderPublicKey;
    }

    public void setActorDestinationPublicKey(String actorDestinationPublicKey) {
        this.actorDestinationPublicKey = actorDestinationPublicKey;
    }

    public void setActorSenderAlias(String actorSenderAlias) {
        this.actorSenderAlias = actorSenderAlias;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public void setActorProtocolState(ActorProtocolState actorProtocolState) {
        this.actorProtocolState = actorProtocolState;
    }

    public UUID getResponseToNotificationId() {
        return responseToNotificationId;
    }

    public void setResponseToNotificationId(UUID responseToNotificationId) {
        this.responseToNotificationId = responseToNotificationId;
    }

    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",                        id.toString());
        jsonObject.addProperty("actorDestinationType",      actorDestinationType.toString());
        jsonObject.addProperty("actorSenderType",           actorSenderType.toString());
        jsonObject.addProperty("actorSenderPublicKey",      actorSenderPublicKey);
        jsonObject.addProperty("actorDestinationPublicKey", actorDestinationPublicKey);
        jsonObject.addProperty("actorSenderAlias",          actorSenderAlias);
        jsonObject.addProperty("actorSenderPhrase",         actorSenderPhrase);
        jsonObject.addProperty("actorSenderProfileImage",   Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT));
        jsonObject.addProperty("notificationDescriptor",    notificationDescriptor.toString());
        jsonObject.addProperty("sentDate",                  sentDate);
        jsonObject.addProperty("actorProtocolState",        actorProtocolState.toString());
        jsonObject.addProperty("flagReadead",               flagReadead);
        jsonObject.addProperty("sentCount",                 sentCount);
        if(responseToNotificationId!=null)jsonObject.addProperty("responseToNotificationId", responseToNotificationId.toString());
        return gson.toJson(jsonObject);

    }


    public static ActorNetworkServiceRecord fronJson(String jsonString){

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new ActorNetworkServiceRecord(jsonObject, gson);
    }


    @Override
    public String toString() {
        return "ActorNetworkServiceRecord{" +
                "id=" + id +
                ", actorDestinationType=" + actorDestinationType +
                ", actorSenderType=" + actorSenderType +
                ", actorSenderPublicKey='" + actorSenderPublicKey + '\'' +
                ", actorDestinationPublicKey='" + actorDestinationPublicKey + '\'' +
                ", actorSenderAlias='" + actorSenderAlias + '\'' +
                ", actorSenderPhrase='" + actorSenderPhrase + '\'' +
                ", actorSenderProfileImage=" + Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT) + //Arrays.toString(actorSenderProfileImage) +
                ", notificationDescriptor=" + notificationDescriptor +
                ", sentDate=" + sentDate +
                ", actorProtocolState=" + actorProtocolState +
                ", flagReadead=" + flagReadead +
                ", sentCount=" + sentCount +
                '}';
    }
}
