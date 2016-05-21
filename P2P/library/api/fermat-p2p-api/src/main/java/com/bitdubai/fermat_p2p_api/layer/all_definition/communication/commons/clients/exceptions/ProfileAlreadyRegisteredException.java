package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileAlreadyRegisteredException</code>
 * is thrown when the profile that we're trying to register is already registered.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ProfileAlreadyRegisteredException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PROFILE ALREADY REGISTERED EXCEPTION";

    public ProfileAlreadyRegisteredException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ProfileAlreadyRegisteredException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
