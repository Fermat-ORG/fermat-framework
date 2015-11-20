package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException</code>
 * is thrown when an error occurs trying to get a contact for a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactNotFoundException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "WALLET CONTACT NOT FOUND EXCEPTION";

    public WalletContactNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WalletContactNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WalletContactNotFoundException(final String message) {
        this(message, null);
    }

    public WalletContactNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
