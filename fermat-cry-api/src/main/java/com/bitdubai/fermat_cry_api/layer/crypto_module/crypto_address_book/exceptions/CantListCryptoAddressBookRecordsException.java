package com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantListCryptoAddressBookRecordsException</code>
 * is thrown when i cant list the instances of the requested CryptoAddressBookRecords.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 * @version 1.0
 */
public class CantListCryptoAddressBookRecordsException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST CRYPTO ADDRESS BOOK RECORDS EXCEPTION";

    public CantListCryptoAddressBookRecordsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
