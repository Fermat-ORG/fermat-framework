package org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions;

/**
 * Created by franklin on 15/10/15.
 */
public class CantRegisterActorAssetRedeemPointException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T REGISTER NEW USER";

    public CantRegisterActorAssetRedeemPointException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterActorAssetRedeemPointException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterActorAssetRedeemPointException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterActorAssetRedeemPointException(final String message) {
        this(message, null);
    }

    public CantRegisterActorAssetRedeemPointException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRegisterActorAssetRedeemPointException() {
        this(DEFAULT_MESSAGE);
    }
}
