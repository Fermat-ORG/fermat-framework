package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantIdentifyVaultException</code>
 * is thrown when we can't identify a crypto vault with whom work.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class CantIdentifyVaultException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T IDENTIFY VAULT EXCEPTION";

    public CantIdentifyVaultException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantIdentifyVaultException(String context) {
        this(DEFAULT_MESSAGE, null, context, null);
    }

}
