package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException</code>
 * is thrown when there is an error trying to load the key pair.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/11/2015.
 */
public class CantLoadKeyPairException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LOAD KEY PAIR EXCEPTION";

    public CantLoadKeyPairException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantLoadKeyPairException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadKeyPairException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
