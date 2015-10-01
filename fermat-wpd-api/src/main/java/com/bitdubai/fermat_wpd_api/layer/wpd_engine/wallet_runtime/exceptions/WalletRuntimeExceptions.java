package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 01/07/15.
 */
public class WalletRuntimeExceptions extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE REQUESTED RUNTIME HAS TRIGGERED AN EXCEPTION";

    public WalletRuntimeExceptions(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}