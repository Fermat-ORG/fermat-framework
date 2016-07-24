package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 27.12.15.
 */
public class CantIdentifyVaultException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. CAN'T IDENTIFY VAULT EXCEPTION";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantIdentifyVaultException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantIdentifyVaultException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param context a String that provides the values of the variables that could have affected the exception
     */
    public CantIdentifyVaultException(String context) {
        this(DEFAULT_MESSAGE, null, context, null);
    }

}
