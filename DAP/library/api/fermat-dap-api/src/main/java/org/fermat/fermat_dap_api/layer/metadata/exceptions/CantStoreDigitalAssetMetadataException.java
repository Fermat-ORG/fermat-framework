package org.fermat.fermat_dap_api.layer.metadata.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by rodrigo on 4/10/16.
 */
public class CantStoreDigitalAssetMetadataException extends DAPException {
    public CantStoreDigitalAssetMetadataException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStoreDigitalAssetMetadataException(Exception cause, String context, String possibleReason) {
        super(cause, context, possibleReason);
    }

    public CantStoreDigitalAssetMetadataException(String message, Exception cause) {
        super(message, cause);
    }

    public CantStoreDigitalAssetMetadataException(String message) {
        super(message);
    }

    public CantStoreDigitalAssetMetadataException(Exception exception) {
        super(exception);
    }

    public CantStoreDigitalAssetMetadataException() {
    }
}
