package org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 02/09/15.
 */
public class CantAskConnectionActorAssetException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T ASK AN ACTOR ASSET FOR ACCEPTANCE EXCEPTION.";

    public CantAskConnectionActorAssetException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAskConnectionActorAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
