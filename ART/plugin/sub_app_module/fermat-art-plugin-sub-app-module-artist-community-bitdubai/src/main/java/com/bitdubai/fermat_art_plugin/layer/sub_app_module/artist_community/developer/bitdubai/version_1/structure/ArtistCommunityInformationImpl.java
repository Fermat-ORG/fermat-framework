package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class ArtistCommunityInformationImpl implements ArtistCommunityInformation {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final ConnectionState connectionState;
    private final UUID connectionId;
    private final ArtistExternalPlatformInformation artistExternalPlatformInformation;

    public ArtistCommunityInformationImpl(final String publicKey,
                                                        final String alias,
                                                        final byte[] image) {

        this.publicKey = publicKey  ;
        this.alias     = alias      ;
        this.image     = image      ;
        this.connectionState = null ;
        this.connectionId = null    ;
        this.artistExternalPlatformInformation = null;
    }

    public ArtistCommunityInformationImpl(final String publicKey,
                                                        final String alias,
                                                        final byte[] image,
                                                        final ConnectionState connectionState,
                                                        final UUID connectionId) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.connectionState    = connectionState;
        this.connectionId       = connectionId   ;
        this.artistExternalPlatformInformation = null;
    }

    public ArtistCommunityInformationImpl(final ArtistActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey() ;
        this.alias     = actorConnection.getAlias()     ;
        this.image     = actorConnection.getImage()     ;
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
        this.artistExternalPlatformInformation = null;
    }

    public ArtistCommunityInformationImpl(final ArtistExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = null                 ;
        this.connectionId = null                    ;
        this.artistExternalPlatformInformation = exposingData.getArtistExternalPlatformInformation();
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }

    @Override
    public List listArtistWallets() {
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
    public ArtistExternalPlatformInformation getArtistExternalPlatformInformation() {
        return this.artistExternalPlatformInformation;
    }
}
