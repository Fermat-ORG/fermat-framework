package com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException</code>
 * is thrown when i cant found an instance of CryptoAddressBook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 * @version 1.0
 */
public class CryptoAddressBookRecordNotFoundException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "REQUESTED ADDRESS BOOK NOT FOUND EXCEPTION";

    public CryptoAddressBookRecordNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
