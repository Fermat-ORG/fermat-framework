package org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class CantConfirmActorAssetNotificationException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM NOTIFICATION EXCEPTION";

    public CantConfirmActorAssetNotificationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmActorAssetNotificationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
