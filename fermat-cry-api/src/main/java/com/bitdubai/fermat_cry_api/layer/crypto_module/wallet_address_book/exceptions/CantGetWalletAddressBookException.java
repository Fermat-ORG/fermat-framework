package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException</code>
 * is thrown when i cant get an instance of WalletAddressBook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class CantGetWalletAddressBookException extends WalletAddressBookException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET WALLET ADDRESS BOOK EXCEPTION";

    public CantGetWalletAddressBookException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetWalletAddressBookException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetWalletAddressBookException(final String message) {
        this(message, null);
    }

    public CantGetWalletAddressBookException() {
        this(DEFAULT_MESSAGE);
    }
}
