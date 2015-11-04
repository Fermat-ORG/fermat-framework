package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;

/**
 * Created by natalia on 01/07/15.
 */
public class CantFactoryReset extends WalletRuntimeExceptions {

    /**
     *
     */
    private static final long serialVersionUID = 4841032427648911456L;

    public static final String DEFAULT_MESSAGE = "CAN'T LOAD REQUESTED STRUCTURE";

    //private final String fileName;

    public CantFactoryReset(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantFactoryReset(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantFactoryReset(final String message) {
        this(message, null);
    }

    public CantFactoryReset(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantFactoryReset() {
        this(DEFAULT_MESSAGE);
    }

}
