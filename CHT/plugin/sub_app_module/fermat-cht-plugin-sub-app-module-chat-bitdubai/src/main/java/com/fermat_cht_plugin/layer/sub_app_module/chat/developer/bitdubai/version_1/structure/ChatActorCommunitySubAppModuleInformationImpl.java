package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;

import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public class ChatActorCommunitySubAppModuleInformationImpl implements ChatActorCommunityInformation {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final ConnectionState connectionState;
    private final UUID connectionId;
    private String status = "";

    public ChatActorCommunitySubAppModuleInformationImpl(final String publicKey,
                                                         final String alias,
                                                         final byte[] image,
                                                         final ConnectionState connectionState,
                                                         final UUID connectionId) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.connectionState    = connectionState;
        this.connectionId       = connectionId   ;
    }


    public ChatActorCommunitySubAppModuleInformationImpl(final ChatActorConnection exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = exposingData.getConnectionState();
        this.connectionId = exposingData.getConnectionId();
        this.status = exposingData.getStatus();
    }

    public ChatActorCommunitySubAppModuleInformationImpl(ChatExposingData ced) {
        this.publicKey = ced.getPublicKey();
        this.alias = ced.getAlias();
        this.image = ced.getImage();
        this.connectionState= null;
        this.connectionId=null;
        this.status=ced.getStatus();
    }

    public ChatActorCommunitySubAppModuleInformationImpl(ChatActorCommunityInformation record) {
        this.publicKey = record.getPublicKey();
        this.alias = record.getAlias();
        this.image = record.getImage();
        this.connectionState = record.getConnectionState();
        this.connectionId = record.getConnectionId();


    }


    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getImage() { return image; }

    @Override
    public List listAlias() {
        return null;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public UUID getConnectionId() {
        return this.connectionId;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ChatActorCommunitySubAppModuleInformationImpl{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", connectionState='" + connectionState + '\'' +
                ", connectionId='" + connectionId + '\'' +
                ", image=" + (image != null) +
                '}';
    }
}
