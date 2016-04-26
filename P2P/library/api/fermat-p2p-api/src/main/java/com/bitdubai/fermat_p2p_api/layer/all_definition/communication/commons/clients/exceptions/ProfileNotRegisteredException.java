package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileNotRegisteredException</code>
 * is thrown when we're trying to unregister a profile not registered yet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ProfileNotRegisteredException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PROFILE NOT REGISTERED EXCEPTION";

    public ProfileNotRegisteredException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ProfileNotRegisteredException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
