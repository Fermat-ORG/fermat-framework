package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException</code>
 * is thrown when i cant get an instance of WalletAddressBookRegistry.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 * @version 1.0
 */
public class CantGetWalletAddressBookRegistryException extends WalletAddressBookException {

    public static final String DEFAULT_MESSAGE = "CANT GET WALLET ADDRESS BOOK REGISTRY EXCEPTION";

    public CantGetWalletAddressBookRegistryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetWalletAddressBookRegistryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetWalletAddressBookRegistryException(final String message) {
        this(message, null);
    }

    public CantGetWalletAddressBookRegistryException() {
        this(DEFAULT_MESSAGE);
    }
}
