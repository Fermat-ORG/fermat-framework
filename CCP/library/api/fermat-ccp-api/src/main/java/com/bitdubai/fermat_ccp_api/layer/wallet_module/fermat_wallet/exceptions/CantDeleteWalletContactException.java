package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The Class <code>CantDeleteWalletContactException</code>
 * is thrown when an error occurs trying to delete A contact from a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantDeleteWalletContactException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE REQUESTED CONTACT EXCEPTION";

    public CantDeleteWalletContactException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteWalletContactException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteWalletContactException(final String message) {
        this(message, null);
    }

    public CantDeleteWalletContactException() {
        this(DEFAULT_MESSAGE);
    }
}
