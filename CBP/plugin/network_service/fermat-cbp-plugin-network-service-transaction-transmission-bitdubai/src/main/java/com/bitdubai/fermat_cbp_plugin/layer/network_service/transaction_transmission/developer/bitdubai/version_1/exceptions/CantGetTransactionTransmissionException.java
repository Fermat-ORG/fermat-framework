package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/11/15.
 */
public class CantGetTransactionTransmissionException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE A RECORD IN DATABASE EXCEPTION";

    public CantGetTransactionTransmissionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetTransactionTransmissionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
