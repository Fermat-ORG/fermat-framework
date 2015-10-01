package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/30/15.
 */
public class CantGetTranslatorException extends FermatException {
    public final static String DEFAULT_MESSAGE = "There was an error trying to get the Translator information";

    public CantGetTranslatorException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
