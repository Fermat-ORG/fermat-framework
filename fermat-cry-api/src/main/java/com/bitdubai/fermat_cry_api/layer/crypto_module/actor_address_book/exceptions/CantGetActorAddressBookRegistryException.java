package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.exceptions.CantGetActorAddressBookRegistryException</code>
 * is thrown when i cant get an instance of ActorAddressBookRegistry.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 * @version 1.0
 */
public class CantGetActorAddressBookRegistryException extends ActorAddressBookException {

    public static final String DEFAULT_MESSAGE = "CANT GET ACTOR ADDRESS BOOK EXCEPTION";

    public CantGetActorAddressBookRegistryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetActorAddressBookRegistryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetActorAddressBookRegistryException(final String message) {
        this(message, null);
    }

    public CantGetActorAddressBookRegistryException() {
        this(DEFAULT_MESSAGE);
    }
}
