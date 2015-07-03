package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException</code>
 * is thrown when i can't register a wallet address book.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class CantRegisterWalletAddressBookException extends WalletAddressBookException {

    public static final String DEFAULT_MESSAGE = "CAN'T REGISTER WALLET ADDRESS BOOK EXCEPTION";

    public CantRegisterWalletAddressBookException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterWalletAddressBookException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterWalletAddressBookException(final String message) {
        this(message, null);
    }

    public CantRegisterWalletAddressBookException() {
        this(DEFAULT_MESSAGE);
    }
}
