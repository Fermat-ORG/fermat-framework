package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 13/10/15.
 */
public class CantStartSubsystemException extends FermatException {

    static final String DEFAULT_MESSAGE = "ERROR STARTING DAP SUB APP MODULE SUBSYSTEM.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
