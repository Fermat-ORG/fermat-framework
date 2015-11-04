package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantSelectEventException</code>
 * is thrown when there is an error trying to select a specific event type.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/10/2015.
 */
public class CantSelectEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SELECT EVENT EXCEPTION";

    public CantSelectEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSelectEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantSelectEventException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
