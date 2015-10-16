package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;
import com.google.gson.Gson;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.15..
 */
public class ActorNetworkServiceRecord implements IntraUserNotification {


    private UUID id;
    private String publicKeyOfTheIntraUserSendingUsANotification;
    private String publicKeyOfTheIntraUserToConnect;
    private String intraUserToConnectAlias;
    private byte[] intraUserToConnectProfileImage;
    private IntraUserNotificationDescriptor  intraUserNotificationDescriptor;

    private Actors actorDestinationType;
    private Actors actorSenderType;
    private String actorSenderPublicKey;
    private String actorDestinationPublicKey;


    public ActorNetworkServiceRecord(String publicKeyOfTheIntraUserSendingUsANotification, String publicKeyOfTheIntraUserToConnect, String intraUserToConnectAlias, byte[] intraUserToConnectProfileImage, IntraUserNotificationDescriptor intraUserNotificationDescriptor) {
        this.publicKeyOfTheIntraUserSendingUsANotification = publicKeyOfTheIntraUserSendingUsANotification;
        this.publicKeyOfTheIntraUserToConnect = publicKeyOfTheIntraUserToConnect;
        this.intraUserToConnectAlias = intraUserToConnectAlias;
        this.intraUserToConnectProfileImage = intraUserToConnectProfileImage;
        this.intraUserNotificationDescriptor = intraUserNotificationDescriptor;
    }

    @Override
    public String getPublicKeyOfTheIntraUserSendingUsANotification() {
        return publicKeyOfTheIntraUserSendingUsANotification;
    }

    @Override
    public String getPublicKeyOfTheIntraUserToConnect() {
        return publicKeyOfTheIntraUserToConnect;
    }

    @Override
    public String getIntraUserToConnectAlias() {
        return intraUserToConnectAlias;
    }

    @Override
    public byte[] getIntraUserToConnectProfileImage() {
        return intraUserToConnectProfileImage;
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
    public IntraUserNotificationDescriptor getNotificationDescriptor() {
        return intraUserNotificationDescriptor;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
