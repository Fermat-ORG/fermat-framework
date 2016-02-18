package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantLoadOrCreateWalletReferenceException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/02/2016.
 */
public class CantLoadOrCreateWalletReferenceException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT LOAD OR CREATE WALLET REFERENCE EXCEPTION";

    public CantLoadOrCreateWalletReferenceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantLoadOrCreateWalletReferenceException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
