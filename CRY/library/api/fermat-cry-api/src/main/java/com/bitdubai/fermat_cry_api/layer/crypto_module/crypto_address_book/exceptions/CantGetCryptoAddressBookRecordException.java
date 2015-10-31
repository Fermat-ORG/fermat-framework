package com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException</code>
 * is thrown when i cant get an instance of CryptoAddressBookRecord.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 * @version 1.0
 */
public class CantGetCryptoAddressBookRecordException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET REQUESTED CRYPTO ADDRESS BOOK EXCEPTION";

    public CantGetCryptoAddressBookRecordException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
