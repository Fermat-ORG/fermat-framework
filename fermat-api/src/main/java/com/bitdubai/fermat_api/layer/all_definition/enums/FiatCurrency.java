package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency</code>
 * Lists all the Fiat Currencies for fermat.
 * Created by ciencias on 20.12.14.
 * Modified by Manuel Perez on 03/08/2015
 * Updated by PatricioGesualdi - (pmgesualdi@hotmail.com) on 18/11/2015.
 */
public enum FiatCurrency implements FermatEnum {
    /**
     * In order to make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ARGENTINE_PESO  ("ARS", Country.ARGENTINA),
    CANADIAN_DOLLAR ("CAD", Country.CANADA),
    US_DOLLAR       ("USD", Country.UNITED_STATES_OF_AMERICA),

    ;

    private final String code;
    private final Country country;

    FiatCurrency(final String code, final Country country) {
        this.code = code;
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }

    public static FiatCurrency getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ARS": return FiatCurrency.ARGENTINE_PESO;
            case "CAD": return FiatCurrency.CANADIAN_DOLLAR;
            case "USD": return FiatCurrency.US_DOLLAR;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the FiatCurrency enum");
        }
    }

    @Override
    public String getCode() { return this.code; }

}