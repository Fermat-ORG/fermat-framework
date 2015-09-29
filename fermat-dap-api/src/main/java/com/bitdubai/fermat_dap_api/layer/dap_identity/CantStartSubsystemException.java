package com.bitdubai.fermat_dap_api.layer.dap_identity;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantStartSubsystemException extends DAPException {

    static final String DEFAULT_MESSAGE = "ERROR STARTING DAP Identity subsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
