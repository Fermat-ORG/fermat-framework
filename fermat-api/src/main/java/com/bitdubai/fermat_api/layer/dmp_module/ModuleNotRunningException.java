package com.bitdubai.fermat_api.layer.dmp_module;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 25.01.15.
 */
public class ModuleNotRunningException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = -4017482723826661109L;
    private static final String DEFAULT_MESSAGE = "THE MODULE IS NOT RUNNING";

    public ModuleNotRunningException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }

    public ModuleNotRunningException(final String message, final Exception cause) {
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public ModuleNotRunningException(final String message) {
        this(message, null);
    }

    public ModuleNotRunningException() {
        this("");
    }
}
