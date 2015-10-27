package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Languages implements FermatEnum {

    ENGLISH("ENGLISH"),
    AMERICAN_ENGLISH("AMERICAN_ENGLISH"),
    GREAT_BRITAIN_ENGLISH("GREAT_BRITAIN_ENGLISH"),
    SPANISH("SPANISH"),
    LATIN_AMERICAN_SPANISH("LATIN_AMERICAN_SPANISH");

    private String code;

    public String value() {
        return this.code;
    }

    public static Languages fromValue(String code) {
        switch (code) {
            case "ENGLISH":                return Languages.ENGLISH;
            case "AMERICAN_ENGLISH":       return Languages.AMERICAN_ENGLISH;
            case "GREAT_BRITAIN_ENGLISH":  return Languages.GREAT_BRITAIN_ENGLISH;
            case "SPANISH":                return Languages.SPANISH;
            case "LATIN_AMERICAN_SPANISH": return Languages.LATIN_AMERICAN_SPANISH;
            //Modified by Manuel Perez on 03/08/2015
            //default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Languages enum");
            default:                       return Languages.ENGLISH;
        }
    }

    Languages(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
