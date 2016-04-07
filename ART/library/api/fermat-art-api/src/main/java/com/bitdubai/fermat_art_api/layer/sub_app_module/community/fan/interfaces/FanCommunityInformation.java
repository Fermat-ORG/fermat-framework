package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface FanCommunityInformation {
    /**
     * This method returns the public key
     * @return the public key of the crypto broker
     */
    String getPublicKey();

    /**
     * This method returns the alias     *
     * @return the name of the crypto broker
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
}
