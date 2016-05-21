package com.bitdubai.fermat_ccp_api.layer.actor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

import java.io.Serializable;

/**
 * *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.User</code> is a interface
 *     that define the methods for management the Users settings.
 *
 * Created by natalia on 20/07/15.
 *
 */

public interface Actor extends Serializable {

    String getActorPublicKey();

    String getName();

    String getPhrase();

    Actors getType();

    byte[] getPhoto();

    String createMessageSignature(String message) throws CantSignExtraUserMessageException;

}
