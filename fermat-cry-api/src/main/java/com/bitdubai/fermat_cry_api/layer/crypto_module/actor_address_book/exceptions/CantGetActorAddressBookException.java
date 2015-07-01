package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException</code>
 * is thrown when there's an exception trying to get an actoraddressbook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/15.
 * @version 1.0
 */
public class CantGetActorAddressBookException extends ActorAddressBookException {

	public static final String DEFAULT_MESSAGE = "CANT GET ACTOR ADDRESS BOOK EXCEPTION";

	public CantGetActorAddressBookException(String message, Exception cause, String context, String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantGetActorAddressBookException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantGetActorAddressBookException(final String message) {
		this(message, null);
	}

	public CantGetActorAddressBookException() {
		this(DEFAULT_MESSAGE);
	}
}
