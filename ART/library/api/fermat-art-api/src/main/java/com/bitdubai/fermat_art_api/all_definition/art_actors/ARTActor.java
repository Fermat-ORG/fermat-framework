package com.bitdubai.fermat_art_api.all_definition.art_actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public interface ARTActor {
    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
     *
     * @return the publicKey
     */
    String getActorPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented a Actor
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getType</code> gives us the Enum of the represented a Actor
     *
     * @return Enum Actors
     */
    Actors getType();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented a Actor
     *
     * @return the image
     */
    byte[] getProfileImage();
}
