package com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.enums.HTTPErrorResponse;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetSongException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class CantDownloadSongException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT DOWNLOAD SONG";

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

    public CantDownloadSongException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDownloadSongException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantDownloadSongException(
            Exception cause,
            String context,
            String possibleReason,
            CantGetSongException e) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
        this.errorCode = e.getErrorCode();
        this.errorMessage = e.getErrorMessage();
        this.httpErrorResponse = e.getHttpErrorResponse();
    }

    public CantDownloadSongException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDownloadSongException(final String message) {
        this(message, null);
    }

    public CantDownloadSongException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantDownloadSongException() {
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


