package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantRequestCryptoAddressException</code>
 * is thrown when there is an error trying to request a crypto address.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class CantRequestCryptoAddressException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REQUEST CRYPTO ADDRESS EXCEPTION";

    public CantRequestCryptoAddressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestCryptoAddressException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
