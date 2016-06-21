package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions;

/**
 * Created by franklin on 15/10/15.
 */
public class CantRegisterActorAssetIssuerException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T REGISTER NEW ISSUER";

    public CantRegisterActorAssetIssuerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterActorAssetIssuerException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterActorAssetIssuerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterActorAssetIssuerException(final String message) {
        this(message, null);
    }

    public CantRegisterActorAssetIssuerException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRegisterActorAssetIssuerException() {
        this(DEFAULT_MESSAGE);
    }
}
