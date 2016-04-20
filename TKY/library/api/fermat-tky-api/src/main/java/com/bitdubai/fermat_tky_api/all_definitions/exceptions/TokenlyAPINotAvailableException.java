package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/04/16.
 */
public class TokenlyAPINotAvailableException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "THE TOKENLY API IS NOT AVAILABLE";

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
     * Represents the TokenlyAPIStatus, if this exception is thrown, the API is not available.
     */
    private final TokenlyAPIStatus tokenlyApiStatus = TokenlyAPIStatus.UNAVAILABLE;

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public TokenlyAPINotAvailableException(
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
    public TokenlyAPINotAvailableException(
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
    public TokenlyAPINotAvailableException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public TokenlyAPINotAvailableException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public TokenlyAPINotAvailableException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor that sets the error code and error message
     */
    public TokenlyAPINotAvailableException(
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
     * Constructor
     */
    public TokenlyAPINotAvailableException() {
        this(DEFAULT_MESSAGE);
    }

    public TokenlyAPINotAvailableException(HTTPErrorResponseException e){
        super(DEFAULT_MESSAGE);
        this.errorMessage = e.errorMessage;
        this.errorCode = e.errorCode;
        this.httpErrorResponse = e.httpErrorResponse;
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
     * This method returns the TokenlyAPIStatus.
     * @return
     */
    public TokenlyAPIStatus getTokenlyApiStatus() {
        return tokenlyApiStatus;
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

    @Override
    public String toString() {
        return "TokenlyAPINotAvailableException{" +
                "errorCode=" + errorCode +
                ", httpErrorResponse=" + httpErrorResponse +
                ", tokenlyApiStatus=" + tokenlyApiStatus +
                '}';
    }
}
