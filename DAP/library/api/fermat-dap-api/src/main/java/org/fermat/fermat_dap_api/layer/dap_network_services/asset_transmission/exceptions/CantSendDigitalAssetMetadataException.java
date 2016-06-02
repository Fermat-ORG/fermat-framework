package org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by root on 06/10/15.
 */
public class CantSendDigitalAssetMetadataException extends DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND DIGITAL ASSET METADATA";

    public CantSendDigitalAssetMetadataException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendDigitalAssetMetadataException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendDigitalAssetMetadataException(final String message) {
        this(message, null);
    }

    public CantSendDigitalAssetMetadataException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendDigitalAssetMetadataException() {
        this(DEFAULT_MESSAGE);
    }
}
