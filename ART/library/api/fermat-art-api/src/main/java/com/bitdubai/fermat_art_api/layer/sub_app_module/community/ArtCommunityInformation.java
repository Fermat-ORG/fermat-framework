package com.bitdubai.fermat_art_api.layer.sub_app_module.community;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/05/16.
 */
public interface ArtCommunityInformation extends Serializable {
    /**
     * This method returns the public key
     * @return the public key of the Fan
     */
    String getPublicKey();

    /**
     * This method returns the alias     *
     * @return the name of the Fan
     */
    String getAlias();

    /**
     * This method returns the profile image
     * @return the profile image
     */
    byte[] getImage();

    /**
     * This method returns the connection state
     * @return ConnectionState object
     */
    ConnectionState getConnectionState();

    /**
     * This method returns ths connection UUID
     * @return UUID object
     */
    UUID getConnectionId();

    /**
     * This method returns the ArtCommunityInformation Type.
     * @return
     */
    Actors getActorType();

    /**
     * This method sets the actor type.
     * @return
     */
    void setActorType(Actors actorType);

    /**
     * This method returns the Art External Platform.
     * @return
     */
    ArtExternalPlatform getArtExternalPlatform();

    /**
     * This method sets the Art External Platform.
     */
    void setArtExternalPlatform(ArtExternalPlatform artExternalPlatform);
}
