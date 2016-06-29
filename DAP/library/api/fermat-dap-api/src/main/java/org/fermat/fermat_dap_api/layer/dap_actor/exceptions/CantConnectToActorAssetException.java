package org.fermat.fermat_dap_api.layer.dap_actor.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 02/11/15.
 */
public class CantConnectToActorAssetException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error connecting to the Actors";

    public CantConnectToActorAssetException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
