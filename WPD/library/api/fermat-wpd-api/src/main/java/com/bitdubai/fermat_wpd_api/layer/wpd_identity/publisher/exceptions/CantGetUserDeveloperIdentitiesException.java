package com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 2015.07.09..
 * Refactored by Manuel Perez on 04/12/2015
 */
public class CantGetUserDeveloperIdentitiesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET USER DEVELOPER IDENTITIES EXCEPTION";

    public CantGetUserDeveloperIdentitiesException(String message, String context, String possibleReason) {
        this(message, null, context, possibleReason);
    }

    public CantGetUserDeveloperIdentitiesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetUserDeveloperIdentitiesException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
