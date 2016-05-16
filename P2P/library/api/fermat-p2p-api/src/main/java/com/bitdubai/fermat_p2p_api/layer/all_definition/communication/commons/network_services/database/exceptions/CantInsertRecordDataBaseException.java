package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantInsertRecordDataBaseException</code>
 * is thrown when there is an error trying to insert a record in database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInsertRecordDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INSERT RECORD IN DATABASE EXCEPTION";

    public CantInsertRecordDataBaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInsertRecordDataBaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInsertRecordDataBaseException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
