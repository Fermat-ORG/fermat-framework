package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressRequestEventException</code>
 * is thrown when there is an error trying to handle a crypto address request event.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class CantHandleCryptoAddressReceivedEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESS REQUEST EVENT EXCEPTION";

    public CantHandleCryptoAddressReceivedEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause, String context) {
        this(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
