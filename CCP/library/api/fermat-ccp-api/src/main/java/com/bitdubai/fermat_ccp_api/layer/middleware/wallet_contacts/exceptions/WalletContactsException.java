package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exception.WalletContactsException</code>
 * is thrown when there's an exception in WalletContacts Plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class WalletContactsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE WALLET CONTACTS HAS TRIGGERED AN EXCEPTION";

    public WalletContactsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WalletContactsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WalletContactsException(final String message) {
        this(message, null);
    }

    public WalletContactsException() {
        this(DEFAULT_MESSAGE);
    }
}
