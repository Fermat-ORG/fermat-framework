package com.bitdubai.fermat_cht_api.all_definition.exceptions;

/**
 * Created by Franklin Marcano on 09/01/16.
 */
public class CantDeleteGroupMemberException extends CHTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT DELETE AN GROUP MEMBER IN DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantDeleteGroupMemberException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantDeleteGroupMemberException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantDeleteGroupMemberException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantDeleteGroupMemberException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantDeleteGroupMemberException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantDeleteGroupMemberException() {
        this(DEFAULT_MESSAGE);
    }
}

