package com.bitdubai.smartwallet.core;

/**
 * Created by ciencias on 20.12.14.
 */
public enum FiatCurrency {
    US_DOLLAR ("USD", Country.UNITED_STATES_OF_AMERICA),
    CANADIAN_DOLLAR   ("CAD", Country.CANADA),
    ARGENTINE_PESO   ("Peso", Country.ARGENTINA);

    private final String mCode;
    private final Country mCountry;

    FiatCurrency(String Code, Country Country) {
        this.mCode = Code;
        this.mCountry = Country;
    }

    public String Code()   { return mCode; }
    public Country Country() { return mCountry; }

}