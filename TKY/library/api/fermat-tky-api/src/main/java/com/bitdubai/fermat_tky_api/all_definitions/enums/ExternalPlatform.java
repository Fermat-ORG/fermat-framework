package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public enum ExternalPlatform implements FermatEnum, Serializable {

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

    /**
     * Returns an Array with all the platforms.
     * @return
     */
    public static List<String> getArrayItems(){
        List<String> platformsNames = new ArrayList<String>();
        ExternalPlatform[] externalPlatforms = values();
        for (ExternalPlatform externalPlatform : externalPlatforms) {
            platformsNames.add(externalPlatform.name());
        }
        return  platformsNames;
    }
    public static ExternalPlatform getExternalPlatformByLabel(String label){
        for (ExternalPlatform externalPlatform :
                values()) {
            if(externalPlatform.name().equals(label.toUpperCase()))
                return externalPlatform;
        }
        return null;
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





