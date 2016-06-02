package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions;

/**
 * Created by eze on 2015.07.29..
 */
public class CantGetAssetUserActorsException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    public static final String DEFAULT_MESSAGE = "There was an exception while trying to load the asset user actors list.";

    public CantGetAssetUserActorsException(Exception cause) {
        super(DEFAULT_MESSAGE, cause, null, null);
    }

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantGetAssetUserActorsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
