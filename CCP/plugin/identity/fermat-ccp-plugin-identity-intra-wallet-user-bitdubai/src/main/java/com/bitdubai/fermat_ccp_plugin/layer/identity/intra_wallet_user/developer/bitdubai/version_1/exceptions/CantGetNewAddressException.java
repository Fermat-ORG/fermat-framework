package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantGetNewAddressException</code>
 * is thrown when we can't get a new address.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class CantGetNewAddressException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET NEW ADDRESS.";

    public CantGetNewAddressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetNewAddressException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}