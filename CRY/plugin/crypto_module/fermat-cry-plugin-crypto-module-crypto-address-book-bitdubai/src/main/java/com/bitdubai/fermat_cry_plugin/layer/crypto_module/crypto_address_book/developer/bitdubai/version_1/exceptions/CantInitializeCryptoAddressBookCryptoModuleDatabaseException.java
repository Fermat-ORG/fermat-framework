package com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The Class <code>package com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoAddressBookCryptoModuleDatabaseException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTO ADDRESS BOOK CRYPTO MODULE DATABASE EXCEPTION";

    public CantInitializeCryptoAddressBookCryptoModuleDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoAddressBookCryptoModuleDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoAddressBookCryptoModuleDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoAddressBookCryptoModuleDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}