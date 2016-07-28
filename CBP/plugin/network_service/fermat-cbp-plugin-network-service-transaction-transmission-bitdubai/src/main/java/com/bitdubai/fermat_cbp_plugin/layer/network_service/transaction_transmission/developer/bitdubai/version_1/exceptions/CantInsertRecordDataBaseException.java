package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.network_service.exceptions.CantInsertRecordDataBaseException</code>
 * is thrown when there is an error trying to insert a record in database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */
public class CantInsertRecordDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INSERT RECORD IN DATABASE EXCEPTION";

    public CantInsertRecordDataBaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInsertRecordDataBaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
