package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/11/15.
 */
public class PendingRequestNotFoundException extends CBPException {

    private static final String DEFAULT_MESSAGE = "PENDING REQUEST NOT FOUND EXCEPTION";

    public PendingRequestNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public PendingRequestNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
