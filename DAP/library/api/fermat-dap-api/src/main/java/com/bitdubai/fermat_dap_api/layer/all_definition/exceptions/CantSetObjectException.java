package com.bitdubai.fermat_dap_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class CantSetObjectException extends DAPException {

    static final String DEFAULT_MESSAGE = "There was an error setting an object.";

    public CantSetObjectException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantSetObjectException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
