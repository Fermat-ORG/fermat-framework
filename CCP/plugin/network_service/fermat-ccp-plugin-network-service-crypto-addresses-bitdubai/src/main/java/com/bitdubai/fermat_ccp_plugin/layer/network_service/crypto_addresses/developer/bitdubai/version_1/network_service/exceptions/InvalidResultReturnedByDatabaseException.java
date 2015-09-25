package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/24/15.
 */
public class InvalidResultReturnedByDatabaseException extends FermatException {
    static final String DEFAULT_MESSAGE = "The result returned by the database is unexpected.";

    public InvalidResultReturnedByDatabaseException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
