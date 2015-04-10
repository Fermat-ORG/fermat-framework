package com.bitdubai.fermat_api.layer._1_definition.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 20.12.14.
 */
public enum FiatCurrency {
    US_DOLLAR ("USD", Country.UNITED_STATES_OF_AMERICA),
    CANADIAN_DOLLAR   ("CAD", Country.CANADA),
    ARGENTINE_PESO   ("ARS", Country.ARGENTINA);

    private final String code;
    private final Country country;
  

    FiatCurrency(String code, Country country) {
        this.code = code;
        this.country = country;
    }

    public String getCode()   { return this.code; }
    public Country getCountry() { return this.country; }
    
    public static FiatCurrency getByCode(String code) {
        
        switch (code) {
            case "USD": return FiatCurrency.US_DOLLAR;
            case "CAD": return FiatCurrency.CANADIAN_DOLLAR;
            case "ARS": return FiatCurrency.ARGENTINE_PESO;
        }

        /**
         * Return by default.
         */
        return FiatCurrency.US_DOLLAR;
    }
}