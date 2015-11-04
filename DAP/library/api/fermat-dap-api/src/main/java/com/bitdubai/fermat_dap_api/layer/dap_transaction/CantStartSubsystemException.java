package com.bitdubai.fermat_dap_api.layer.dap_transaction;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class CantStartSubsystemException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error staring the DAPTransaction subsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
