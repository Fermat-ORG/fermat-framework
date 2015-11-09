package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantDeleteRecordDataBaseException</code>
 * is thrown when there is an error trying to delete a record from database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/11/2015.
 */
public final class CantDeleteRecordDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE RECORD IN DATABASE EXCEPTION";

    public CantDeleteRecordDataBaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteRecordDataBaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantDeleteRecordDataBaseException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
