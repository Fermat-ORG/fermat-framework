package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookException;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException</code>
 * is thrown when i can't intialize the actor address book.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class CantInitializeActorAddressBookCryptoModuleException extends ActorAddressBookException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO MODULE ACTOR ADDRESS BOOK";

    public CantInitializeActorAddressBookCryptoModuleException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeActorAddressBookCryptoModuleException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeActorAddressBookCryptoModuleException(final String message) {
        this(message, null);
    }

    public CantInitializeActorAddressBookCryptoModuleException() {
        this(DEFAULT_MESSAGE);
    }
}
