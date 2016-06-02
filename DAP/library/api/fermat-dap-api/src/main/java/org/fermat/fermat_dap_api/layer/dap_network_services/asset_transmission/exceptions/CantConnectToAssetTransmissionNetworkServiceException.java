package org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions;

/**
 * Created by root on 06/10/15.
 */
public class CantConnectToAssetTransmissionNetworkServiceException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CONNECT TO ASSET TRANSMISSION NETWORK SERVICE";

    public CantConnectToAssetTransmissionNetworkServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConnectToAssetTransmissionNetworkServiceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantConnectToAssetTransmissionNetworkServiceException(final String message) {
        this(message, null);
    }

    public CantConnectToAssetTransmissionNetworkServiceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantConnectToAssetTransmissionNetworkServiceException() {
        this(DEFAULT_MESSAGE);
    }
}
