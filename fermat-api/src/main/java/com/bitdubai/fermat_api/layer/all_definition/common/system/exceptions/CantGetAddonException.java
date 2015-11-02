package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException</code>
 * is thrown when there is an error trying to get an addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 */
public class CantGetAddonException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET ADDON EXCEPTION";

    public CantGetAddonException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetAddonException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
