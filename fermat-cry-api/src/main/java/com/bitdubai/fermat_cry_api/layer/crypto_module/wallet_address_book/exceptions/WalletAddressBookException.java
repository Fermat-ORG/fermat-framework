package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookException</code>
 * is thrown when there's an exception in WalletAddressBook Plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class WalletAddressBookException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "THE WALLET ADDRESS BOOK HAS TRIGGERED AN EXCEPTION";

    public WalletAddressBookException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WalletAddressBookException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WalletAddressBookException(final String message) {
        this(message, null);
    }

    public WalletAddressBookException() {
        this(DEFAULT_MESSAGE);
    }
}
