package org.fermat.fermat_dap_api.layer.all_definition.exceptions;

/**
 * Created by rodrigo on 2/22/16.
 */
public class CantGetAssetNegotiationsException extends DAPException {

    public static final String DEFAULT_MESSAGE = "There was an error getting the asset negotiation";

    public CantGetAssetNegotiationsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetAssetNegotiationsException(Exception exception) {
        super(exception);
    }
}
