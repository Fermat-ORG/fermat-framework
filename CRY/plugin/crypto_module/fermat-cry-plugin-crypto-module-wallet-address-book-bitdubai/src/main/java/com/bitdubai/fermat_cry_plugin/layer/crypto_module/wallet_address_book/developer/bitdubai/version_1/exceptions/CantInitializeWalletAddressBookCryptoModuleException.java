package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookException;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException</code>
 * is thrown when i can't intialize the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class CantInitializeWalletAddressBookCryptoModuleException extends WalletAddressBookException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO MODULE WALLET ADDRESS BOOK";

    public CantInitializeWalletAddressBookCryptoModuleException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWalletAddressBookCryptoModuleException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWalletAddressBookCryptoModuleException(final String message) {
        this(message, null);
    }

    public CantInitializeWalletAddressBookCryptoModuleException() {
        this(DEFAULT_MESSAGE);
    }
}
