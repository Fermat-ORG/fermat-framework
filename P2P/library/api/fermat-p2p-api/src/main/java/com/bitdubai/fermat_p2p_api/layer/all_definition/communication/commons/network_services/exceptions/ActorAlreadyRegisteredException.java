package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException</code>
 * is thrown when there is an error trying to register an actor which is already registered.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorAlreadyRegisteredException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR ALREADY REGISTERED EXCEPTION";

    public ActorAlreadyRegisteredException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorAlreadyRegisteredException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
    public ActorAlreadyRegisteredException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
