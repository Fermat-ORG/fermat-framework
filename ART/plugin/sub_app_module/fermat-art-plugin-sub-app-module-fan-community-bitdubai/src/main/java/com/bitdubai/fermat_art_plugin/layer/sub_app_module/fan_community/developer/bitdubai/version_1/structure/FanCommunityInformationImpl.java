package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;

import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/5/16.
 */
public class FanCommunityInformationImpl implements FanCommunityInformation {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final ConnectionState connectionState;
    private final UUID connectionId;
    private final FanExternalPlatformInformation fanExternalPlatformInformation;


    public FanCommunityInformationImpl(final String publicKey,
                                                          final String alias,
                                                          final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.fanExternalPlatformInformation = null;
        this.connectionState = null;
        this.connectionId = null;
    }

    public FanCommunityInformationImpl(final String publicKey,
                                                          final String alias,
                                                          final byte[] image,
                                                          final ConnectionState connectionState,
                                                          final UUID connectionId) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.fanExternalPlatformInformation = null;
        this.connectionState    = connectionState;
        this.connectionId       = connectionId   ;
    }

    public FanCommunityInformationImpl(final FanActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
        this.fanExternalPlatformInformation = null;
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();

    }

    public FanCommunityInformationImpl(final FanExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.fanExternalPlatformInformation = exposingData.getFanExternalPlatformInformation();
        this.connectionState = null;
        this.connectionId = null;
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

    @Override
    public ConnectionState getConnectionState() {
        return connectionState;
    }

    @Override
    public UUID getConnectionId() {
        return connectionId;
    }

    @Override
    public FanExternalPlatformInformation getFanExternalPlatformInformation() {
        return this.fanExternalPlatformInformation;
    }
}
