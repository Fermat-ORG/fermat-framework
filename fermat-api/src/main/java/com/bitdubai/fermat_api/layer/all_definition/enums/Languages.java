package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Languages {
    ENGLISH("ENGLISH"),
    AMERICAN_ENGLISH("AENGLISH"),
    GREAT_BRITAIN_ENGLISH("GBENGLISH"),
    SPANISH("SPANISH"),
    LATIN_AMERICAN_SPANISH("LASPANISH");

    private String code;

    public String value() { return this.code; }

    public static Languages fromValue(String code){
        switch(code){
            case "ENGLISH":
                return ENGLISH ;
            case "AMERICAN_ENGLISH":
                return AMERICAN_ENGLISH ;
            case "GREAT_BRITAIN_ENGLISH":
                return GREAT_BRITAIN_ENGLISH ;
            case "SPANISH":
                return SPANISH;
            case "LATIN_AMERICAN_SPANISH":
                return LATIN_AMERICAN_SPANISH;
            //Modified by Manuel Perez on 03/08/2015
            //default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Languages enum");
            default: return Languages.ENGLISH;
        }
    }

    Languages(String code) {
        this.code = code;
    }
}
