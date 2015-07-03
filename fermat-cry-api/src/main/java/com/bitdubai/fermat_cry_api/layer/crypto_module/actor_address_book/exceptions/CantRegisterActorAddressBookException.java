package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBookException</code>
 * is thrown when there's an exception registering an actoraddressbook.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/15.
 * @version 1.0
 */
public class CantRegisterActorAddressBookException extends ActorAddressBookException {

	public static final String DEFAULT_MESSAGE = "CANT REGISTER ACTOR ADDRESS BOOK EXCEPTION";

	public CantRegisterActorAddressBookException(String message, Exception cause, String context, String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantRegisterActorAddressBookException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantRegisterActorAddressBookException(final String message) {
		this(message, null);
	}

	public CantRegisterActorAddressBookException() {
		this(DEFAULT_MESSAGE);
	}
}
