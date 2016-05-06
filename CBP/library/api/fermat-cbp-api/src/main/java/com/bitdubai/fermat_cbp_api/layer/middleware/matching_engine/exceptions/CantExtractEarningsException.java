package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public class CantExtractEarningsException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Cant Transfer the earnings to the Earning Wallet";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantExtractEarningsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Create a {@link CantExtractEarningsException} with DEFAULT_MESSAGE, cause, context and possibleReason
     *
     * @param cause          the exception that triggered the throwing of the current exception
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantExtractEarningsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Create a {@link CantExtractEarningsException} with DEFAULT_MESSAGE, null, context and possibleReason
     *
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantExtractEarningsException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
