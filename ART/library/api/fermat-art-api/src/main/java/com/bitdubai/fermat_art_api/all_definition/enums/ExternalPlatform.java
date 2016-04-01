package com.bitdubai.fermat_art_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public enum ExternalPlatform implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    TOKENLY("TOK"),
    UNDEFINED("UND");

    /**
     * sets the default platform that will be used at start up.
     */
    public static final ExternalPlatform DEFAULT_EXTERNAL_PLATFORM = ExternalPlatform.TOKENLY;

    String code;
    ExternalPlatform(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static ExternalPlatform getByCode(String code) throws InvalidParameterException {
        for (ExternalPlatform value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ExternalPlatform enum.");
    }

    @Override
    public String toString() {
        return "ExternalPlatform{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}





