package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException</code>
 * is thrown when there is an error trying to update a record in database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateRecordDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE A RECORD IN DATABASE EXCEPTION";

    public CantUpdateRecordDataBaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRecordDataBaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
