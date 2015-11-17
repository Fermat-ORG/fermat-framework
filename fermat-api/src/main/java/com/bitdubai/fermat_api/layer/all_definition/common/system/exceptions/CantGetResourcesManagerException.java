package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetResourcesManagerException</code>
 * is thrown when there is an error trying to get a resources manager.
 * <p/>
 * Created by lnacosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class CantGetResourcesManagerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT' GET RESOURCES MANAGER EXCEPTION";

    public CantGetResourcesManagerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetResourcesManagerException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetResourcesManagerException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
