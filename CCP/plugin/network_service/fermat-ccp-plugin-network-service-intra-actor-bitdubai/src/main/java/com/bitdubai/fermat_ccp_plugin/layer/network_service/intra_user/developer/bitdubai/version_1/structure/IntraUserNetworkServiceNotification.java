package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;


import java.util.UUID;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkServiceNotification implements IntraUserNotification {

    private String intraUserToConnectPublicKey;
    private String intraUserLogedInPublicKey;
    private NotificationDescriptor notificationDescriptor;
    private byte[] profileImage;
    private String alias;


    public IntraUserNetworkServiceNotification(String intraUserLogedInPublicKey,String alias,String intraUserToConnectPublicKey, NotificationDescriptor notificationDescriptor, byte[] profileImage)
    {
        this.intraUserLogedInPublicKey = intraUserLogedInPublicKey;
        this.notificationDescriptor = notificationDescriptor;
        this.intraUserToConnectPublicKey = intraUserToConnectPublicKey;
        this.profileImage = (byte[])profileImage.clone();
        this.alias = alias;
    }


    @Override
    public String getActorSenderAlias(){
        return this.alias;
    }

    @Override
    public  byte[] getActorSenderProfileImage(){
        return this.profileImage;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public Actors getActorDestinationType() {
        return null;
    }

    @Override
    public String getActorDestinationPublicKey() {
        return null;
    }

    @Override
    public String getActorSenderPublicKey() {
        return null;
    }

    @Override
    public Actors getActorSenderType() {
        return null;
    }

    @Override
    public void setFlagReadead(boolean flagReadead) {

    }

    @Override
    public NotificationDescriptor getNotificationDescriptor() {
        return this.notificationDescriptor;
    }

    @Override
    public long getSentDate() {
        return 0;
    }
}
