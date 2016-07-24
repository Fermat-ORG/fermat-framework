package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 26.01.15.
 */
public class CantCreateDefaultWalletsException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = -4872388934935205345L;
    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE THE EVENT";

    public CantCreateDefaultWalletsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }

    public CantCreateDefaultWalletsException(final String message, final Exception cause) {
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public CantCreateDefaultWalletsException(final String message) {
        this(message, null);
    }

    public CantCreateDefaultWalletsException() {
        this("");
    }
}
