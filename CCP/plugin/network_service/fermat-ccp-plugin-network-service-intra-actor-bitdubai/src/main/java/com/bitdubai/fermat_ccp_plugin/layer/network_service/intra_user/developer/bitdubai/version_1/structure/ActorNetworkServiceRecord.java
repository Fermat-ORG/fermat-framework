package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.google.gson.Gson;

import java.util.Arrays;
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
    private byte[] actorSenderProfileImage;
    private NotificationDescriptor notificationDescriptor;
    private long sentDate;
    private ActorProtocolState actorProtocolState;
    private boolean flagReadead;

    public ActorNetworkServiceRecord(UUID id, String actorSenderAlias, byte[] actorSenderProfileImage, NotificationDescriptor notificationDescriptor, Actors actorDestinationType, Actors actorSenderType, String actorSenderPublicKey, String actorDestinationPublicKey,long sentDate,ActorProtocolState actorProtocolState,boolean flagReadead) {
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
    }

    @Override
    public String getActorSenderAlias() {
        return actorSenderAlias;
    }

    @Override
    public byte[] getActorSenderProfileImage() {
        return (actorSenderProfileImage!=null) ? (byte[] )this.actorSenderProfileImage.clone() : null;
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

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
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
                ", actorSenderProfileImage=" + Arrays.toString(actorSenderProfileImage) +
                ", notificationDescriptor=" + notificationDescriptor +
                ", sentDate=" + sentDate +
                ", actorProtocolState=" + actorProtocolState +
                ", flagReadead=" + flagReadead +
                '}';
    }
}
