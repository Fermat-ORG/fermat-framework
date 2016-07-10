package org.fermat.fermat_dap_api.layer.metadata.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by rodrigo on 4/10/16.
 */
public class CantGetDigitalAssetMetadataException extends DAPException {
    public CantGetDigitalAssetMetadataException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetDigitalAssetMetadataException(Exception cause, String context, String possibleReason) {
        super(cause, context, possibleReason);
    }

    public CantGetDigitalAssetMetadataException(String message, Exception cause) {
        super(message, cause);
    }

    public CantGetDigitalAssetMetadataException(String message) {
        super(message);
    }

    public CantGetDigitalAssetMetadataException(Exception exception) {
        super(exception);
    }

    public CantGetDigitalAssetMetadataException() {
    }
}
