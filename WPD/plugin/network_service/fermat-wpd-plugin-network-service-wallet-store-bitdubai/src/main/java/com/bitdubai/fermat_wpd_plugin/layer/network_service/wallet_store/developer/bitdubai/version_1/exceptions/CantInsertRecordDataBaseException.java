package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 8/11/15.
 */
public class CantInsertRecordDataBaseException extends FermatException {
    public final static String DEFAULT_MESSAGE = "There was an error trying to insert a database record.";

    public CantInsertRecordDataBaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
