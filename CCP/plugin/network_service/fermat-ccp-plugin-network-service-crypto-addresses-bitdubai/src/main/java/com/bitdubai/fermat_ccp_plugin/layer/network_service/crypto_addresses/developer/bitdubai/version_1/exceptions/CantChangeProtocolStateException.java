package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException</code>
 * is thrown when there is an error trying to change the protocol state to an specific address exchange request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public class CantChangeProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE PROTOCOL STATE EXCEPTION";

    public CantChangeProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
