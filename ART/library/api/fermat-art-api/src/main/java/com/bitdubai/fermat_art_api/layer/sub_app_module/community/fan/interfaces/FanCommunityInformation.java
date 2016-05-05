package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExternalPlatformInformation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface FanCommunityInformation extends Serializable{
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
     * The method <code>getArtistExternalPlatformInformation</code> returns the ArtistExternalPlatformInformation this actor has with the selected actor
     * @return ArtistExternalPlatformInformation object
     */
    FanExternalPlatformInformation getFanExternalPlatformInformation();
}
