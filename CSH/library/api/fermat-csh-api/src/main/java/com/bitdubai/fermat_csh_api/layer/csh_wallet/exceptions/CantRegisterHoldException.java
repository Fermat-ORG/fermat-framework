package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantRegisterHoldException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Register a Hold transaction in this Cash Money Wallet";

    public CantRegisterHoldException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterHoldException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterHoldException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantRegisterHoldException(final String message) {
        this(message, null, null, null);
    }

    public CantRegisterHoldException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRegisterHoldException() {
        this(DEFAULT_MESSAGE);
    }

}
