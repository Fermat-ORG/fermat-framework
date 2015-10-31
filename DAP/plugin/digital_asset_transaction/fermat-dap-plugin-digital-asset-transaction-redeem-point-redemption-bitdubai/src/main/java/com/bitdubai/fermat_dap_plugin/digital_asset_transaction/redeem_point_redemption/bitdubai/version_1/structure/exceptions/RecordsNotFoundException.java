package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 26/10/15.
 */
public class RecordsNotFoundException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "Couldn't find any record in database.";

    //CONSTRUCTORS

    public RecordsNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RecordsNotFoundException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public RecordsNotFoundException(String message, Exception cause) {
        super(message, cause);
    }

    public RecordsNotFoundException(String message) {
        super(message);
    }

    public RecordsNotFoundException(Exception exception) {
        super(exception);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
