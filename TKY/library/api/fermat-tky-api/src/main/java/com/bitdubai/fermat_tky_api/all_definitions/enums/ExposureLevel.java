package com.bitdubai.fermat_tky_api.all_definitions.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public enum ExposureLevel implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    PUBLIC("PUB"),
    PRIVATE("PRI");

    /**
     * sets the default exposure level that will be used at start up.
     */
    private static final ExposureLevel DEFAULT_EXPOSURE_LEVEL = ExposureLevel.PUBLIC;

    String code;
    ExposureLevel(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static ExposureLevel getByCode(String code) throws InvalidParameterException {
        for (ExposureLevel value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(
                InvalidParameterException.DEFAULT_MESSAGE,
                null, "Code Received: " + code,
                "This Code Is Not Valid for the ExposureLevel enum.");
    }

    @Override
    public String toString() {
        return "ExposureLevel{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }


    public static List<String> getArrayItems(){
        List<String> platformsNames = new ArrayList<String>();
        ExposureLevel[] externalPlatforms = values();
        for (ExposureLevel externalPlatform : externalPlatforms) {
            platformsNames.add(externalPlatform.name());
        }
        return  platformsNames;
    }
}
