package com.bitdubai.fermat_cht_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin 31-03-2016
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