package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class CantActorAssetPendingNotificationsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ERROR GETTING NOTIFICATIONS";

    public CantActorAssetPendingNotificationsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantActorAssetPendingNotificationsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
