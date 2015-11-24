package com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/11/15.
 */
public class CantCheckAppropriationStatsException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "There was an error while checking the appropriation stats.";

    //CONSTRUCTORS

    public CantCheckAppropriationStatsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
