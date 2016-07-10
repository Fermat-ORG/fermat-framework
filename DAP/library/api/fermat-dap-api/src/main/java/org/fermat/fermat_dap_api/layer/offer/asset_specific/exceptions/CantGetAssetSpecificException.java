package org.fermat.fermat_dap_api.layer.offer.asset_specific.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */

public class CantGetAssetSpecificException extends DAPException {

    public CantGetAssetSpecificException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetAssetSpecificException(Exception cause, String context, String possibleReason) {
        super(cause, context, possibleReason);
    }

    public CantGetAssetSpecificException(String message, Exception cause) {
        super(message, cause);
    }

    public CantGetAssetSpecificException(String message) {
        super(message);
    }

    public CantGetAssetSpecificException(Exception exception) {
        super(exception);
    }

    public CantGetAssetSpecificException() {
    }
}
