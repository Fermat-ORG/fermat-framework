package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by root on 06/10/15.
 */
public class CantRequestCryptoAddressException extends DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST CRYPTO ADDRESS";


    public CantRequestCryptoAddressException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestCryptoAddressException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestCryptoAddressException(final String message) {
        this(message, null);
    }

    public CantRequestCryptoAddressException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRequestCryptoAddressException() {
        this(DEFAULT_MESSAGE);
    }
}
