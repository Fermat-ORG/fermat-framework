package com.bitdubai.fermat_art_api.layer.actor_network_service.util;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.NotificationDescriptor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorNotification;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistActor;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * Created by Gabriel Araujo 09/03/16.
 */
public class ArtistActorNetworkServiceRecord implements ActorNotification, ArtistActor {
    private UUID id;
    private PlatformComponentType actorDestinationType;
    private PlatformComponentType actorSenderType;
    private String actorSenderPublicKey;
    private String actorDestinationPublicKey;
    private String actorSenderAlias;
    private byte[] actorSenderProfileImage;
    private NotificationDescriptor notificationDescriptor;
    private String externalUsername;
    private String externalAccesToken;
    private ExternalPlatform externalPlatform;
    private ExposureLevel exposureLevel;
    private ArtistAcceptConnectionsType artistAcceptConnectionsType;
    private long sentDate;
    private ProtocolState protocolState;
    private boolean flagRead;
    private int sentCount;

    private String messageXML;
    private UUID responseToNotificationId;


    public ArtistActorNetworkServiceRecord(){

    }

    public ArtistActorNetworkServiceRecord(String alias, String publickey, byte[] imageProfile){
        this.actorSenderAlias = alias;
        this.actorSenderPublicKey = publickey;
        this.actorSenderProfileImage = imageProfile;
    }
    public ArtistActorNetworkServiceRecord(Artist artist){
        this.actorSenderAlias = artist.getAlias();
        this.actorSenderPublicKey = artist.getPublicKey();
        this.actorSenderProfileImage = artist.getProfileImage();
        this.externalUsername = artist.getExternalUsername();
        this.externalAccesToken = artist.getExternalAccesToken();
        this.externalPlatform = artist.getExternalPlatform();
        this.exposureLevel = artist.getExposureLevel();
        this.artistAcceptConnectionsType = artist.getArtistAcceptConnectionsType();
    }
    public ArtistActorNetworkServiceRecord(UUID id,
                                           String actorSenderAlias,
//                                         String actorSenderPhrase,
                                           byte[] actorSenderProfileImage,
                                           NotificationDescriptor notificationDescriptor,
                                           PlatformComponentType actorDestinationType,
                                           PlatformComponentType actorSenderType,
                                           String actorSenderPublicKey,
                                           String actorDestinationPublicKey,
                                           long sentDate,
                                           ProtocolState protocolState,
                                           boolean flagRead,
                                           int sendCount,
                                           UUID responseToNotificationId,
                                           String messageXML) {

        this.id                             = id;
        this.actorSenderAlias               = actorSenderAlias;
        this.actorSenderProfileImage        = actorSenderProfileImage;
        this.notificationDescriptor = notificationDescriptor;
        this.actorDestinationType           = actorDestinationType;
        this.actorSenderType                = actorSenderType;
        this.actorSenderPublicKey           = actorSenderPublicKey;
        this.actorDestinationPublicKey      = actorDestinationPublicKey;
        this.sentDate                       = sentDate;
        this.protocolState = protocolState;
        this.flagRead                       = flagRead;
        this.sentCount                      = sendCount;
        this.responseToNotificationId       = responseToNotificationId;
        this.messageXML = messageXML;
    }


    private ArtistActorNetworkServiceRecord(JsonObject jsonObject, Gson gson) {


        this.id =                           UUID.fromString(jsonObject.get("id").getAsString());
        this.actorSenderAlias =             (jsonObject.get("actorSenderAlias") != null) ? jsonObject.get("actorSenderAlias").getAsString() : null;
        this.actorSenderProfileImage =      Base64.decode(jsonObject.get("actorSenderProfileImage").getAsString(), Base64.DEFAULT);
        this.notificationDescriptor =  gson.fromJson(jsonObject.get("notificationDescriptor").getAsString(), NotificationDescriptor.class);
        this.actorDestinationType =         gson.fromJson(jsonObject.get("actorDestinationType").getAsString(), PlatformComponentType.class);
        this.actorSenderType =              gson.fromJson(jsonObject.get("actorSenderType").getAsString(), PlatformComponentType.class);
        this.actorSenderPublicKey =         jsonObject.get("actorSenderPublicKey").getAsString();
        this.actorDestinationPublicKey =    jsonObject.get("actorDestinationPublicKey").getAsString();
        this.sentDate =                     jsonObject.get("sentDate").getAsLong();
        this.protocolState =      gson.fromJson(jsonObject.get("protocolState").getAsString(), ProtocolState.class);
        this.flagRead =                  jsonObject.get("flagRead").getAsBoolean();
        this.sentCount =                    jsonObject.get("sentCount").getAsInt();

        if (jsonObject.get("messageXML") != null)
            this.messageXML       =  jsonObject.get("messageXML").getAsString();
        if (jsonObject.get("responseToNotificationId") != null)
            this.responseToNotificationId = UUID.fromString(jsonObject.get("responseToNotificationId").getAsString());

    }


    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",                            id.toString());
        jsonObject.addProperty("actorDestinationType",          actorDestinationType.toString());
        jsonObject.addProperty("actorSenderType",               actorSenderType.toString());
        jsonObject.addProperty("actorSenderPublicKey",          actorSenderPublicKey);
        jsonObject.addProperty("actorDestinationPublicKey",     actorDestinationPublicKey);
        jsonObject.addProperty("actorSenderAlias",              actorSenderAlias);
        jsonObject.addProperty("actorSenderProfileImage",       Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT));
        jsonObject.addProperty("notificationDescriptor", notificationDescriptor.toString());
        jsonObject.addProperty("sentDate",                      sentDate);
        jsonObject.addProperty("protocolState", protocolState.toString());
        jsonObject.addProperty("flagRead",                      flagRead);
        jsonObject.addProperty("sentCount",                     sentCount);
        if (messageXML != null)
            jsonObject.addProperty("messageXML",                messageXML);
        if (responseToNotificationId != null)
            jsonObject.addProperty("responseToNotificationId",  responseToNotificationId.toString());
        return gson.toJson(jsonObject);

    }


    public static ArtistActorNetworkServiceRecord fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new ArtistActorNetworkServiceRecord(jsonObject, gson);
    }

    @Override
    public String toString() {
        String profileImageUser = null;
        if(actorSenderProfileImage != null)
            profileImageUser = Base64.encodeToString(actorSenderProfileImage, Base64.DEFAULT);

        return "AssetUserNetworkServiceRecord{" +
                "id=" + id +
                ", actorDestinationType=" + actorDestinationType +
                ", actorSenderType=" + actorSenderType +
                ", actorSenderPublicKey='" + actorSenderPublicKey + '\'' +
                ", actorDestinationPublicKey='" + actorDestinationPublicKey + '\'' +
                ", actorSenderAlias='" + actorSenderAlias + '\'' +
                ", actorSenderProfileImage=" + profileImageUser +
                ", notificationDescriptor=" + notificationDescriptor +
                ", sentDate=" + sentDate +
                ", protocolState=" + protocolState +
                ", flagRead=" + flagRead +
                ", sentCount=" + sentCount +
                ", messageXML=" + messageXML +
                ", responseToNotificationId=" + responseToNotificationId +
                '}';
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public PlatformComponentType getActorDestinationType() {
        return actorDestinationType;
    }

    public void setActorDestinationType(PlatformComponentType actorDestinationType) {
        this.actorDestinationType = actorDestinationType;
    }

    @Override
    public PlatformComponentType getActorSenderType() {
        return actorSenderType;
    }

    public void setActorSenderType(PlatformComponentType actorSenderType) {
        this.actorSenderType = actorSenderType;
    }

    @Override
    public String getActorSenderPublicKey() {
        return actorSenderPublicKey;
    }

    public void setActorSenderPublicKey(String actorSenderPublicKey) {
        this.actorSenderPublicKey = actorSenderPublicKey;
    }

    @Override
    public String getActorDestinationPublicKey() {
        return actorDestinationPublicKey;
    }

    public void setActorDestinationPublicKey(String actorDestinationPublicKey) {
        this.actorDestinationPublicKey = actorDestinationPublicKey;
    }

    @Override
    public String getActorSenderAlias() {
        return actorSenderAlias;
    }

    public void setActorSenderAlias(String actorSenderAlias) {
        this.actorSenderAlias = actorSenderAlias;
    }

    @Override
    public byte[] getActorSenderProfileImage() {
        return actorSenderProfileImage;
    }

    public void setActorSenderProfileImage(byte[] actorSenderProfileImage) {
        this.actorSenderProfileImage = actorSenderProfileImage;
    }

    public NotificationDescriptor getNotificationDescriptor() {
        return notificationDescriptor;
    }

    public void setNotificationDescriptor(NotificationDescriptor notificationDescriptor) {
        this.notificationDescriptor = notificationDescriptor;
    }

    @Override
    public long getSentDate() {
        return sentDate;
    }

    public void setSentDate(long sentDate) {
        this.sentDate = sentDate;
    }

    public ProtocolState getProtocolState() {
        return protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
    }

    public boolean isFlagRead() {
        return flagRead;
    }

    @Override
    public void setFlagRead(boolean flagRead) {
        this.flagRead = flagRead;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    @Override
    public String getMessageXML() {
        return messageXML;
    }

    public void setMessageXML(String messageXML) {
        this.messageXML = messageXML;
    }

    public UUID getResponseToNotificationId() {
        return responseToNotificationId;
    }

    public void setResponseToNotificationId(UUID responseToNotificationId) {
        this.responseToNotificationId = responseToNotificationId;
    }

    @Override
    public ExposureLevel getExposureLevel() {
        return exposureLevel;
    }

    @Override
    public ArtistAcceptConnectionsType getArtistAcceptConnectionsType() {
        return artistAcceptConnectionsType;
    }

    @Override
    public String getAlias() {
        return actorSenderAlias;
    }

    @Override
    public String getPublicKey() {
        return actorSenderPublicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return actorSenderProfileImage;
    }

    @Override
    public void setNewProfileImage(byte[] imageBytes) {
        this.actorSenderProfileImage = imageBytes;
    }

    @Override
    public String getExternalUsername() {
        return externalUsername;
    }

    @Override
    public String getExternalAccesToken() {
        return externalAccesToken;
    }

    @Override
    public ExternalPlatform getExternalPlatform() {
        return externalPlatform;
    }

    public void setExternalUsername(String externalUsername) {
        this.externalUsername = externalUsername;
    }

    public void setExternalAccesToken(String externalAccesToken) {
        this.externalAccesToken = externalAccesToken;
    }

    public void setExternalPlatform(ExternalPlatform externalPlatform) {
        this.externalPlatform = externalPlatform;
    }

    public void setExposureLevel(ExposureLevel exposureLevel) {
        this.exposureLevel = exposureLevel;
    }

    public void setArtistAcceptConnectionsType(ArtistAcceptConnectionsType artistAcceptConnectionsType) {
        this.artistAcceptConnectionsType = artistAcceptConnectionsType;
    }
}