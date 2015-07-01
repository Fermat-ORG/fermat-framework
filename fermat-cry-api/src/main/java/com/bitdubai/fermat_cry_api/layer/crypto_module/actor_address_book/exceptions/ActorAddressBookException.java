package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookException</code>
 * is thrown when there's an exception in ActorAddressBook Plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class ActorAddressBookException extends CryptoException {

    public static final String DEFAULT_MESSAGE = "THE ACTOR ADDRESS BOOK PLUGIN HAS TRIGGERED AN EXCEPTION";

    public ActorAddressBookException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorAddressBookException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public ActorAddressBookException(final String message) {
        this(message, null);
    }

    public ActorAddressBookException() {
        this(DEFAULT_MESSAGE);
    }
}
