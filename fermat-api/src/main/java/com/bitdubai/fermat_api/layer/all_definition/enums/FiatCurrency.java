package com.bitdubai.fermat_api.layer.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.12.14.
 */
public enum FiatCurrency implements FermatEnum {

    US_DOLLAR       ("USD", Country.UNITED_STATES_OF_AMERICA),
    CANADIAN_DOLLAR ("CAD", Country.CANADA),
    ARGENTINE_PESO  ("ARS", Country.ARGENTINA);

    private String code;
    private Country country;

    FiatCurrency(String code, Country country) {
        this.code = code;
        this.country = country;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public Country getCountry() {
        return this.country;
    }

    public static FiatCurrency getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "USD": return FiatCurrency.US_DOLLAR;
            case "CAD": return FiatCurrency.CANADIAN_DOLLAR;
            case "ARS": return FiatCurrency.ARGENTINE_PESO;
            //Modified by Manuel Perez on 03/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the FiatCurrency enum");
        }
        /**
         * Return by default.
         */
        //return FiatCurrency.US_DOLLAR;
    }
}