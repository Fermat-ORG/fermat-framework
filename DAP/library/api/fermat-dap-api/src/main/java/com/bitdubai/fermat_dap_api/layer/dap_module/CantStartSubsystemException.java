package com.bitdubai.fermat_dap_api.layer.dap_module;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 11/09/15.
 */
public class CantStartSubsystemException extends FermatException {
    static final String DEFAULT_MESSAGE = "ERROR STARTING DAPModuleSubsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
