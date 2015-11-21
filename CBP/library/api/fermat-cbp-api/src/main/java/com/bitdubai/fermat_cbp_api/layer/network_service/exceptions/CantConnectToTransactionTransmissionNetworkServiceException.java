package com.bitdubai.fermat_cbp_api.layer.network_service.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class CantConnectToTransactionTransmissionNetworkServiceException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CONNECT TO TRANSACTION TRANSMISSION NETWORK SERVICE";

    public CantConnectToTransactionTransmissionNetworkServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConnectToTransactionTransmissionNetworkServiceException(final Exception exception, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, exception, context, possibleReason);
    }

    public CantConnectToTransactionTransmissionNetworkServiceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantConnectToTransactionTransmissionNetworkServiceException(final String message) {
        this(message, null);
    }

    public CantConnectToTransactionTransmissionNetworkServiceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantConnectToTransactionTransmissionNetworkServiceException() {
        this(DEFAULT_MESSAGE);
    }

}
