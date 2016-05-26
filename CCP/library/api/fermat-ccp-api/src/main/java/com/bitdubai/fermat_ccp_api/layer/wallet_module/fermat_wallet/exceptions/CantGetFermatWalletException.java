package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The interface <code>CantGetFermatWalletException</code>
 * is thrown when i cant RETURN the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class CantGetFermatWalletException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO REQUESTED EXCEPTION";

    public CantGetFermatWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetFermatWalletException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetFermatWalletException(final String message) {
        this(message, null);
    }

    public CantGetFermatWalletException() {
        this(DEFAULT_MESSAGE);
    }
}
