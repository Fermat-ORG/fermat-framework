package com.bitdubai.fermat_api.layer.all_definition.exceptions;

/**
 * Created by eze on 2015.06.18..
 */

public class InvalidParameterException extends Exception {
    private static String defaultMsg = "Wrong Parameter Found: ";

    public InvalidParameterException() {
        super(defaultMsg);
    }

    public InvalidParameterException(String newMsg) {
        super(defaultMsg + newMsg);
    }
}
