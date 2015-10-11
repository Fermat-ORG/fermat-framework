package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions;

/**
 * The Exception <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantDeleteCryptoAddressException</code>
 * is thrown when an error occurs trying to delete a crypto address to a contact.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantDeleteCryptoAddressException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE CRYPTO ADDRESS TO WALLET CONTACT EXCEPTION";

    public CantDeleteCryptoAddressException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteCryptoAddressException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteCryptoAddressException(final String message) {
        this(message, null);
    }

    public CantDeleteCryptoAddressException() {
        this(DEFAULT_MESSAGE);
    }
}
