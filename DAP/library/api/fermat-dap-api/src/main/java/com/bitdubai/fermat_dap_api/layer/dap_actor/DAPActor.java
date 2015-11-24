package com.bitdubai.fermat_dap_api.layer.dap_actor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

/**
 * Created by Nerio on 10/09/15.
 */
public interface DAPActor {

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

//    String createMessageSignature(String message) throws CantSignExtraUserMessageException;
}
