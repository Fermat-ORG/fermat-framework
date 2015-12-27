package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nelson Ramirez
 * @since 21/12/15.
 */
public class CantGetNegotiationInformationException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Cant get the negotiation information. Please check de negotiation ID";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantGetNegotiationInformationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Call CantGetNegotiationInformationException(DEFAULT_MESSAGE, cause, "", "")
     *
     * @param cause he exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     */
    public CantGetNegotiationInformationException(Exception cause) {
        super(DEFAULT_MESSAGE, cause, "", "");
    }
}
