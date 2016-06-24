package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException</code>
 * is thrown when there is an error trying to update a registered profile.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantUpdateRegisteredProfileException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T UPDATE REGISTERED PROFILE EXCEPTION";

    public CantUpdateRegisteredProfileException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRegisteredProfileException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantUpdateRegisteredProfileException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
