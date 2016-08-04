package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantDeleteRecordDataBaseException</code>
 * is thrown when we can't find a record in database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/11/2015.
 */
public class RecordNotFoundException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T RECORD NOT FOUND IN DATABASE EXCEPTION";

    public RecordNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RecordNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public RecordNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
