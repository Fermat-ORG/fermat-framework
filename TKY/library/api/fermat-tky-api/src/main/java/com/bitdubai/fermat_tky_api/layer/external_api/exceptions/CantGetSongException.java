package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class CantGetSongException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SONG OBJECT FROM JSON RESPONSE";

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

    public CantGetSongException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSongException(
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
    public CantGetSongException(
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

    public CantGetSongException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetSongException(final String message) {
        this(message, null);
    }

    public CantGetSongException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetSongException() {
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


