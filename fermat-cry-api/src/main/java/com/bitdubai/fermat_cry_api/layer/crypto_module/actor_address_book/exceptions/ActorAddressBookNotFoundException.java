package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException</code>
 * is thrown when i cant found an instance of ActorAddressBook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/06/15.
 * @version 1.0
 */
public class ActorAddressBookNotFoundException extends ActorAddressBookException {

    public static final String DEFAULT_MESSAGE = "ACTOR ADDRESS BOOK NOT FOUND EXCEPTION";

    public ActorAddressBookNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorAddressBookNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public ActorAddressBookNotFoundException(final String message) {
        this(message, null);
    }

    public ActorAddressBookNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

}
