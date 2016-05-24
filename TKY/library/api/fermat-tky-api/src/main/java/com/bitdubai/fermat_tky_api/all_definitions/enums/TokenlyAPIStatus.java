package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/04/16.
 */
public enum TokenlyAPIStatus implements FermatEnum, Serializable {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    AVAILABLE("AVA"),
    UNAVAILABLE("UVA"),
    UNDEFINED("UND");

    /**
     * sets the default API status that will be used at start up.
     */
    public static final TokenlyAPIStatus DEFAULT_API_STATUS = TokenlyAPIStatus.UNDEFINED;

    String code;
    TokenlyAPIStatus(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static TokenlyAPIStatus getByCode(String code) throws InvalidParameterException {
        for (TokenlyAPIStatus value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the TokenlyAPIStatus enum.");
    }

    /**
     * Returns an Array with all the API status.
     * @return
     */
    public static List<String> getArrayItems(){
        List<String> statusNames = new ArrayList<String>();
        TokenlyAPIStatus[] tokenlyAPIStatuses = values();
        for (TokenlyAPIStatus tokenlyAPIStatus : tokenlyAPIStatuses) {
            statusNames.add(tokenlyAPIStatus.name());
        }
        return  statusNames;
    }
    public static TokenlyAPIStatus getTokenlyAPIStatusByLabel(String label){
        for (TokenlyAPIStatus tokenlyAPIStatus :
                values()) {
            if(tokenlyAPIStatus.name().equals(label.toUpperCase()))
                return tokenlyAPIStatus;
        }
        return null;
    }

    @Override
    public String toString() {
        return "TokenlyAPIStatus{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}





