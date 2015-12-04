package com.bitdubai.fermat_pip_api.layer.module.developer.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseToolException</code>
 * is thrown when an error occurs trying to get de list of plugins and addon DataBases
 * <p/>
 * Created by Natalia on 02/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetDataBaseToolException extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE DEVELOPER DATABASETOOL HAS TRIGGERED AN EXCEPTION";

    public CantGetDataBaseToolException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}