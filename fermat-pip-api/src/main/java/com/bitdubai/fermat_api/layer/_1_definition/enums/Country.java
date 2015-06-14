package com.bitdubai.fermat_api.layer._1_definition.enums;

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
    public String getCountry() { return mDisplayName; }

}