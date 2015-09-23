package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/22/15.
 */
public class InconsistentDatabaseResultException extends FermatException {
    static public final String DEFAULT_MESSAGE = "The number of records returned by the query is not what was expected.";

    public InconsistentDatabaseResultException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
