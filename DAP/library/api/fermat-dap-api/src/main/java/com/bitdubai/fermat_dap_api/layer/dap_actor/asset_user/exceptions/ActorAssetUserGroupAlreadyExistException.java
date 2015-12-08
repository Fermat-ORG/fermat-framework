package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 03/12/15.
 */
public class ActorAssetUserGroupAlreadyExistException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There is already a group created with this name.";

    //CONSTRUCTORS

    public ActorAssetUserGroupAlreadyExistException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
