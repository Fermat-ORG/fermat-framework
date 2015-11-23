package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantRegisterUnholdException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Register a Hold transaction in this Cash Money Wallet";

    public CantRegisterUnholdException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterUnholdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterUnholdException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantRegisterUnholdException(final String message) {
        this(message, null, null, null);
    }

    public CantRegisterUnholdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRegisterUnholdException() {
        this(DEFAULT_MESSAGE);
    }

}
