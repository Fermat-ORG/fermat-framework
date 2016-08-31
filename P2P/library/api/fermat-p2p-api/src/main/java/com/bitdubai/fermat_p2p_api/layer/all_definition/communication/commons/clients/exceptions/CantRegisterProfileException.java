package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException</code>
 * is thrown when there is an error trying to register a profile.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantRegisterProfileException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T REGISTER PROFILE EXCEPTION";

    public CantRegisterProfileException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterProfileException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterProfileException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
