package org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by root on 06/10/15.
 */
public class CantRequestListAssetTransmissionNetworkServiceException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST LIST ASSET TRANSMISSION NETWORK SERVICE";

    public CantRequestListAssetTransmissionNetworkServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestListAssetTransmissionNetworkServiceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestListAssetTransmissionNetworkServiceException(final String message) {
        this(message, null);
    }

    public CantRequestListAssetTransmissionNetworkServiceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRequestListAssetTransmissionNetworkServiceException() {
        this(DEFAULT_MESSAGE);
    }


}
