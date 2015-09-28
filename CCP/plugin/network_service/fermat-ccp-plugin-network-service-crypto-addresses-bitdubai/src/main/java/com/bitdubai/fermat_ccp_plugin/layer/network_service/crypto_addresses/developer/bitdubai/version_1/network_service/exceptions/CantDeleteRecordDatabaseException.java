package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 8/11/15.
 */
public class CantDeleteRecordDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to delete a record from the database.";
    public CantDeleteRecordDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
