package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface LinkedFanIdentity extends Serializable {

    /**
     * The method <code>getPublicKey</code> returns the UUID of the connected actor to this identity
     */
    UUID getConnectionId();

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented crypto broker
     * @return the public key of the crypto broker
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the name of the represented crypto broker
     *
     * @return the name of the crypto broker
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented crypto broker
     *
     * @return the profile image
     */
    byte[] getImage();


}
