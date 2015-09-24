package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.12.14.
 */
public enum Country {
    UNITED_STATES_OF_AMERICA ("US", "United States"),
    CANADA   ("CA", "Canada"),
    ARGENTINA   ("AR","Argentina");

    private String code;
    private String mDisplayName;

    Country (String code, String DisplayName) {
        this.code = code;
        this.mDisplayName = DisplayName;
    }

    public String getCode()   { return code; }

    public static Country getByCode(String code) throws InvalidParameterException{

        switch (code){

            case "US":
                return Country.UNITED_STATES_OF_AMERICA;
            case "CA":
                return Country.CANADA;
            case "AR":
                return Country.ARGENTINA;
            //Modified by Manuel Perez on 03/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Country enum");

        }

    }

    public String getCountry() { return mDisplayName; }

}