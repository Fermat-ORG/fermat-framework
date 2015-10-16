package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;

import java.util.UUID;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkServiceNotification implements IntraUserNotification {

    private String intraUserToConnectPublicKey;
    private String intraUserLogedInPublicKey;
    private IntraUserNotificationDescriptor notificationDescriptor;
    private byte[] profileImage;
    private String alias;


    public IntraUserNetworkServiceNotification(String intraUserLogedInPublicKey,String alias,String intraUserToConnectPublicKey, IntraUserNotificationDescriptor notificationDescriptor, byte[] profileImage)
    {
        this.intraUserLogedInPublicKey = intraUserLogedInPublicKey;
        this.notificationDescriptor = notificationDescriptor;
        this.intraUserToConnectPublicKey = intraUserToConnectPublicKey;
        this.profileImage = (byte[])profileImage.clone();
        this.alias = alias;
    }

    @Override
    public String getPublicKeyOfTheIntraUserSendingUsANotification() {
        return this.intraUserLogedInPublicKey;
    }

    @Override
    public String getPublicKeyOfTheIntraUserToConnect(){
        return this.intraUserToConnectPublicKey;
    }

    @Override
    public String getIntraUserToConnectAlias(){
        return this.alias;
    }

    @Override
    public  byte[] getIntraUserToConnectProfileImage(){
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
    public IntraUserNotificationDescriptor getNotificationDescriptor() {
        return this.notificationDescriptor;
    }
}
