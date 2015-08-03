package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Languages {
    ENGLISH("ENGLISH"),
    AMERICAN_ENGLISH("AMERICAN_ENGLISH"),
    GREAT_BRITAIN_ENGLISH("GREAT_BRITAIN_ENGLISH"),
    SPANISH("SPANISH"),
    LATIN_AMERICAN_SPANISH("LATIN_AMERICAN_SPANISH");

    public String getCode() { return this.code; }

    public static Languages fromValue(String code) throws InvalidParameterException{
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
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Languages enum");

        }
        //return null;
    }

    private final String code;

    Languages(String code) {
        this.code = code;
    }
}
