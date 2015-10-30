package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantStartAddonDeveloperException</code>
 * is thrown when there is an error trying to start an addon developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class CantStartAddonDeveloperException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START ADDON DEVELOPER EXCEPTION";

    public CantStartAddonDeveloperException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAddonDeveloperException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
