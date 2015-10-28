package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/26/15.
 */
public class CantConnectToAssetUserException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error connecting to the Actor Asset User";

    public CantConnectToAssetUserException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
