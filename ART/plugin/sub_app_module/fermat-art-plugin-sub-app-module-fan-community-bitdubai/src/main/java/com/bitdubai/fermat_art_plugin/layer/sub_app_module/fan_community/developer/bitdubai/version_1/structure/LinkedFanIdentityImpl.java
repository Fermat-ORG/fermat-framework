package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;

import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/5/16.
 */
public class LinkedFanIdentityImpl implements LinkedFanIdentity {

    private final UUID connectionId;
    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;

    public LinkedFanIdentityImpl(final UUID connectionId,
                                            final String publicKey,
                                            final String alias,
                                            final byte[] image) {

        this.connectionId = connectionId;
        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
    }

    public LinkedFanIdentityImpl(final FanActorConnection actorConnection) {

        this.connectionId = actorConnection.getConnectionId();
        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
    }

    @Override
    public UUID getConnectionId() {
        return connectionId;
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
