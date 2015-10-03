package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package CantInitializeWalletContactsMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantDeleteCryptoAddressesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE CRYPTO ADDRESSES EXCEPTION";

    public CantDeleteCryptoAddressesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteCryptoAddressesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteCryptoAddressesException(final String message) {
        this(message, null);
    }

    public CantDeleteCryptoAddressesException() {
        this(DEFAULT_MESSAGE);
    }
}