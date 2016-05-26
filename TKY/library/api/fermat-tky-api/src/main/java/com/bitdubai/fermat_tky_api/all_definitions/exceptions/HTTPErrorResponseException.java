package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/03/16.
 */
public class HTTPErrorResponseException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "HTTP ERROR RESPONSE";

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
    public HTTPErrorResponseException(
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
    public HTTPErrorResponseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor that sets the error code and error message
     */
    public HTTPErrorResponseException(
            int errorCode,
            String errorMessage,
            HTTPErrorResponse httpErrorResponse
            ) {
        super(DEFAULT_MESSAGE);
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
    public HTTPErrorResponseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public HTTPErrorResponseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public HTTPErrorResponseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public HTTPErrorResponseException() {
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

