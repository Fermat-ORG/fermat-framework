package com.bitdubai.android_core.app.common.version_1.apps_manager;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by mati on 2016.06.03..
 */
public class CantOpenSessionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T OPEN SESSION EXCEPTION";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantOpenSessionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantOpenSessionException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
