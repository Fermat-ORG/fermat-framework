package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions;

/**
 * The Exception <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException</code>
 * is thrown when an error occurs trying to add a crypto address to a contact.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantAddCryptoAddressException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "CAN'T ADD CRYPTO ADDRESS TO WALLET CONTACT EXCEPTION";

    public CantAddCryptoAddressException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAddCryptoAddressException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAddCryptoAddressException(final String message) {
        this(message, null);
    }

    public CantAddCryptoAddressException() {
        this(DEFAULT_MESSAGE);
    }
}
