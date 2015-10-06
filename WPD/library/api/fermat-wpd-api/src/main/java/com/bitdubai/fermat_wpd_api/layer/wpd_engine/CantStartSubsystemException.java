package com.bitdubai.fermat_wpd_api.layer.wpd_engine;

import com.bitdubai.fermat_api.layer.WPDException;

/**
 * Created by Nerio on 29/09/15.
 */
public class CantStartSubsystemException extends WPDException {

    static final String DEFAULT_MESSAGE = "ERROR STARTING WPD ENGINE SUBSYSTEM.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
