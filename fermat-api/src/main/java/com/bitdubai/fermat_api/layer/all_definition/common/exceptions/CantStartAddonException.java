package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartAddonException</code>
 * is thrown when there is an error trying to start an addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantStartAddonException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START ADDON EXCEPTION";

    public CantStartAddonException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAddonException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
