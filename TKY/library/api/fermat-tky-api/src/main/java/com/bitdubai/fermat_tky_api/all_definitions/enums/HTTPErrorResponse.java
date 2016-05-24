package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/04/16.
 */
public enum HTTPErrorResponse implements FermatEnum {

    INTERNAL_SERVER_ERROR("500","The Tokenly server had an internal error"),
    SERVICE_UNAVAILABLE("503", "The Tokenly API is temporally offline for maintenance"),
    TOO_MANY_REQUEST("429","Too many request to Tokenly API"),
    UNDEFINED("999", "Undefined response"),
    ;

    String code;
    String errorResponse;

    /**
     * Sets the default external platform.
     */
    private static final HTTPErrorResponse DEFAULT_ERROR_RESPONSE = HTTPErrorResponse.UNDEFINED;

    /**
     * Enum constructor with parameters
     * @param code
     * @param errorResponse
     */
    HTTPErrorResponse(
            final String code,
            final String errorResponse){
        this.code = code;
        this. errorResponse = errorResponse;
    }

    //PUBLIC METHODS
    public static HTTPErrorResponse getByCode(String code)  {
        for (HTTPErrorResponse value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        return DEFAULT_ERROR_RESPONSE;
    }

    @Override
    public String toString() {
        return "SongStatus{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

    /**
     * This method returns the HTTP Error Response text.
     * @return
     */
    public String getErrorResponse() {
        return this.errorResponse;
    }

    /**
     * Returns an Array with all the HTTP Error Responses.
     * @return
     */
    public static List<String> getArrayItems(){
        List<String> platformsNames = new ArrayList<String>();
        HTTPErrorResponse[] httpErrorResponses = values();
        for (HTTPErrorResponse httpErrorResponse : httpErrorResponses) {
            platformsNames.add(httpErrorResponse.name());
        }
        return  platformsNames;
    }

    /**
     * This method returns the HTTP error response by label.
     * @param label
     * @return
     */
    public static HTTPErrorResponse getHTTPErrorResponseByLabel(String label){
        for (HTTPErrorResponse httpErrorResponse :
                values()) {
            if(httpErrorResponse.name().equals(label.toUpperCase()))
                return httpErrorResponse;
        }
        return null;
    }

    /**
     * Gets the default HTTP error response.
     * @return the default HTTPErrorResponse
     */
    public static HTTPErrorResponse getDefaultHTTPErrorResponse(){
        return DEFAULT_ERROR_RESPONSE;
    }

}
