package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.12.14.
 */
public enum Country {
    UNITED_STATES_OF_AMERICA ("US", "United States"),
    CANADA   ("CA", "Canada"),
    ARGENTINA   ("AR","Argentina");

    private final String mCode;
    private final String mDisplayName;

    private Country (String Code, String DisplayName) {
        this.mCode = Code;
        this.mDisplayName = DisplayName;
    }

    public String getCode()   { return mCode; }

    public static String getByCode(String code) throws InvalidParameterException{

        Country country;
        switch (code){

            case "US":
                country=Country.UNITED_STATES_OF_AMERICA;
                return country.mDisplayName;
            case "CA":
                country=Country.CANADA;
                return country.mDisplayName;
            case "AR":
                country=Country.ARGENTINA;
                return country.mDisplayName;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Country enum");


        }

    }

    public String getCountry() { return mDisplayName; }

}