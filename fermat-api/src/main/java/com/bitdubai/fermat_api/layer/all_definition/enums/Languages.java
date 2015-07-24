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


    public static Languages fromValue(String key) {
        switch(key){
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
        }
        return null;
    }

    private final String key;

    Languages(String key) {
        this.key = key;
    }

    public String value() { return this.key; }
}
