package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public class CantStartServiceException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the service.";
    public CantStartServiceException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
