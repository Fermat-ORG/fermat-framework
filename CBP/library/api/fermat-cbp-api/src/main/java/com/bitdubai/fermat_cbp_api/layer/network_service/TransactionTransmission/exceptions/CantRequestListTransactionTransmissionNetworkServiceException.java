package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class CantRequestListTransactionTransmissionNetworkServiceException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST TRANSACTION TRANSMISSION NETWORK SERVICE";

    public CantRequestListTransactionTransmissionNetworkServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestListTransactionTransmissionNetworkServiceException(final Exception exception, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, exception, context, possibleReason);
    }

    public CantRequestListTransactionTransmissionNetworkServiceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestListTransactionTransmissionNetworkServiceException(final String message) {
        this(message, null);
    }

    public CantRequestListTransactionTransmissionNetworkServiceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRequestListTransactionTransmissionNetworkServiceException() {
        this(DEFAULT_MESSAGE);
    }
}
