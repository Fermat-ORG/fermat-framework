package com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The Class <code>package com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.InvalidCryptoAddressBookRecordParametersException</code>
 * is thrown when the params sent to create the register are wrong.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class InvalidCryptoAddressBookRecordParametersException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "INVALID CRYPTO ADDRESS BOOK RECORD PARAMETERS EXCEPTION";

    public InvalidCryptoAddressBookRecordParametersException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InvalidCryptoAddressBookRecordParametersException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public InvalidCryptoAddressBookRecordParametersException(final String message) {
        this(message, null);
    }

    public InvalidCryptoAddressBookRecordParametersException() {
        this(DEFAULT_MESSAGE);
    }
}