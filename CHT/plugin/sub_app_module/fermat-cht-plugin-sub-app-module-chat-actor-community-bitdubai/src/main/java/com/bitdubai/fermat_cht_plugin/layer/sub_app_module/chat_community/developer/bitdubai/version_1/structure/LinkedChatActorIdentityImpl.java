package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;

import java.util.UUID;

public class LinkedChatActorIdentityImpl implements com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.LinkedChatActorIdentity {

    private final UUID connectionId;
    private final String publicKey;
    private final String alias;
    private final byte[] image;

    public LinkedChatActorIdentityImpl(final UUID connectionId,
                                       final String publicKey,
                                       final String alias,
                                       final byte[] image) {

        this.connectionId = connectionId;
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
    }

    public LinkedChatActorIdentityImpl(final ChatActorConnection actorConnection) {

        this.connectionId = actorConnection.getConnectionId();
        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
    }


    @Override
    public UUID getConnectionId() {
        return this.connectionId;
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
    public byte[] getImage() {
        return image;
    }


}