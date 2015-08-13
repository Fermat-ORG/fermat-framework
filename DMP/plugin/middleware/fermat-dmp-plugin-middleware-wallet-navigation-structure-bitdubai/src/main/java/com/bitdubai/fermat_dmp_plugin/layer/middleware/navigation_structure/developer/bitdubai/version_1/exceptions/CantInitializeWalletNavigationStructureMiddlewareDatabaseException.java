package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez on 12/08/15.
 */
public class CantInitializeWalletNavigationStructureMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE REQUESTED NAVIGATION STRUCTURE MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeWalletNavigationStructureMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWalletNavigationStructureMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWalletNavigationStructureMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeWalletNavigationStructureMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
    
}
