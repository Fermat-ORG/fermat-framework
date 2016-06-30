package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 01/04/16.
 */
public interface ExposingData {

    /**
     * @return a string representing the public key.
     */
    String getPublicKey();

    /**
     * @return a string representing the alias of the actor.
     */
    String getAlias();

    /**
     * @return an array of bytes with the image exposed by the Actor.
     */
    byte[] getImage();

    /**
     * This method returns the Actor location
     * @return
     */
    Location getLocation();
}
