package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class CantGenerateTokenlyAuthSignatureException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT GENERATE TOKENLY AUTH SIGNATURE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGenerateTokenlyAuthSignatureException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGenerateTokenlyAuthSignatureException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantGenerateTokenlyAuthSignatureException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantGenerateTokenlyAuthSignatureException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantGenerateTokenlyAuthSignatureException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantGenerateTokenlyAuthSignatureException() {
        this(DEFAULT_MESSAGE);
    }

}

