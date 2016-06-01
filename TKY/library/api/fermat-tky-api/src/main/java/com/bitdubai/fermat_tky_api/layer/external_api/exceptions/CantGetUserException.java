package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class CantGetUserException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT GET THE USER FROM API TOKENLY";
    /**
     * Represents the error message.
     */
    public String errorMessage;

    /**
     * Represents the error code.
     */
    public int errorCode;

    /**
     * Represents the HTTPErrorResponse value.
     */
    HTTPErrorResponse httpErrorResponse;

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGetUserException(
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
    public CantGetUserException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     * @param cause
     * @param context
     * @param possibleReason
     * @param errorMessage
     * @param errorCode
     * @param httpErrorResponse
     */
    public CantGetUserException(
            Exception cause,
            String context,
            String possibleReason,
            String errorMessage,
            int errorCode,
            HTTPErrorResponse httpErrorResponse
    ) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpErrorResponse = httpErrorResponse;
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantGetUserException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantGetUserException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantGetUserException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantGetUserException() {
        this(DEFAULT_MESSAGE);
    }

    /**
     * This method returns the error message
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * This method returns the error code.
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * This method returns the HTTPErrorResponse.
     * @return
     */
    public HTTPErrorResponse getHttpErrorResponse() {
        if(httpErrorResponse ==null){
            return HTTPErrorResponse.getDefaultHTTPErrorResponse();
        }
        return httpErrorResponse;
    }

}

