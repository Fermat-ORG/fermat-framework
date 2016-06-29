package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions-CantGetAllIntraUserConnectionsException</code>
 * is thrown when i cant Get all intra user connections.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 * @version 1.0
 */
public class CantGetAllIntraUserConnectionsException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CANT GET ALL INTRA USER CONNECTIONS EXCEPTION";

    public CantGetAllIntraUserConnectionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetAllIntraUserConnectionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetAllIntraUserConnectionsException(final String message) {
        this(message, null);
    }

    public CantGetAllIntraUserConnectionsException() {
        this(DEFAULT_MESSAGE);
    }
}
