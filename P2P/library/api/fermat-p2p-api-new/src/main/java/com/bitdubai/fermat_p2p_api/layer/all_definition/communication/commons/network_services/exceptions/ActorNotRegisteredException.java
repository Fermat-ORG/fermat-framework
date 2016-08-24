package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorNotRegisteredException</code>
 * is thrown when there is an error trying to do an action over an unregistered actor.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorNotRegisteredException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR NOT REGISTERED EXCEPTION";

    public ActorNotRegisteredException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorNotRegisteredException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ActorNotRegisteredException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
