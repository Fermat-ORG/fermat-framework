package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public class CantSaveEventException extends DAPException{
    public static final String DEFAULT_MESSAGE = "Cannot save the event.";
    public CantSaveEventException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
