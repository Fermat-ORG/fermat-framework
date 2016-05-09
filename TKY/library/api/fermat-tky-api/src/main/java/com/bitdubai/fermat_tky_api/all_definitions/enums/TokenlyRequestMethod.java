package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public enum TokenlyRequestMethod implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    String code;
    TokenlyRequestMethod(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static TokenlyRequestMethod getByCode(String code) throws InvalidParameterException {
        for (TokenlyRequestMethod value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the TokenlyRequestMethod enum.");
    }

    @Override
    public String toString() {
        return "TokenlyRequestMethod{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }

}
