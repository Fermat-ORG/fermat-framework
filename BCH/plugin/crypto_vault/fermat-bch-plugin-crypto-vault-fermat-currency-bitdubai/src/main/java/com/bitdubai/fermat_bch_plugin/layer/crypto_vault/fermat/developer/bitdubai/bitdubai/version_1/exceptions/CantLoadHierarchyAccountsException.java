package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/7/15.
 */
public class CantLoadHierarchyAccountsException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to load all the Hierarchy Accounts in the platform.";
    public CantLoadHierarchyAccountsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
