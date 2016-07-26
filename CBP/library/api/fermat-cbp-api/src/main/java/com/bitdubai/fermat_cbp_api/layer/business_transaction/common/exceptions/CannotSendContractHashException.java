package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/12/15.
 */
public class CannotSendContractHashException extends CBPException {
    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT SEND CONTRACT HASH";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CannotSendContractHashException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CannotSendContractHashException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CannotSendContractHashException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CannotSendContractHashException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CannotSendContractHashException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CannotSendContractHashException() {
        this(DEFAULT_MESSAGE);
    }
}
