package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.WalletContactsException;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeWalletContactsDatabaseException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE REQUESTED CONTACT DATABASE EXCEPTION";

    public CantInitializeWalletContactsDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWalletContactsDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWalletContactsDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeWalletContactsDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
