package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 21/01/16.
 */
public class CantUpdateActorAssetIssuerException extends DAPException {

    //VARIABLE DECLARATION
    public static String DEFAULT_MESSAGE = "Something bad happen while attempting to update the actor asset issuer.";
    //CONSTRUCTORS

    public CantUpdateActorAssetIssuerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantUpdateActorAssetIssuerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateActorAssetIssuerException(String message, Exception cause) {
        super(message, cause);
    }

    public CantUpdateActorAssetIssuerException(String message) {
        super(message);
    }

    public CantUpdateActorAssetIssuerException(Exception exception) {
        super(exception);
    }

    public CantUpdateActorAssetIssuerException() {
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
