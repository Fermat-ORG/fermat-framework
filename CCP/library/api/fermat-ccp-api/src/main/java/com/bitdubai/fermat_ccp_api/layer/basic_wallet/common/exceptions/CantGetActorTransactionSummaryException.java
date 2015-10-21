package com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetActorTransactionSummaryException</code>
 * is thrown when i cant get an actor transaction summary.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 * @version 1.0
 */
public class CantGetActorTransactionSummaryException extends FermatException {
    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantGetActorTransactionSummaryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}