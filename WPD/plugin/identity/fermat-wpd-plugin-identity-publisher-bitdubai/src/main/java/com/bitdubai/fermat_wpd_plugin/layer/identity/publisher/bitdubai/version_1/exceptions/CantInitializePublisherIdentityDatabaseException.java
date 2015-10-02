package com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException</code>
 * is thrown when i can't intialize the actor address book.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * Updated by Raul Pena   - (raul.pena@gmail.com)  on 16/07/15.
 *
 * @version 1.0
 */
public class CantInitializePublisherIdentityDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE PUBLISHER IDENTITY DATABASE EXCEPTION";

    public CantInitializePublisherIdentityDatabaseException(final String message, final String context, final String possibleReason) {
        this (message, null, context, possibleReason);
    }

    public CantInitializePublisherIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
