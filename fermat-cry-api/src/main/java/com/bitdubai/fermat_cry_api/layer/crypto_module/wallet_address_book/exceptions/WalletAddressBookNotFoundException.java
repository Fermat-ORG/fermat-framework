package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookNotFoundException</code>
 * is thrown when i cant found an instance of WalletAddressBook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class WalletAddressBookNotFoundException extends WalletAddressBookException {

    public static final String DEFAULT_MESSAGE = "REQUESTED ADDRESS BOOK NOT FOUND EXCEPTION";

    public WalletAddressBookNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WalletAddressBookNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WalletAddressBookNotFoundException(final String message) {
        this(message, null);
    }

    public WalletAddressBookNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

}
