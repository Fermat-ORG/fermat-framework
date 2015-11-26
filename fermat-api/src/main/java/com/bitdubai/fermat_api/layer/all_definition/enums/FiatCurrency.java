package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency</code>
 * Lists all the Fiat Currencies for fermat.
 * Created by ciencias on 20.12.14.
 * Modified by Manuel Perez on 03/08/2015
 * Updated by PatricioGesualdi - (pmgesualdi@hotmail.com) on 18/11/2015.
 * Modified by Alejandro Bicelis on 20/11/2015
 */
public enum FiatCurrency implements Currency {
    /**
     * In order to make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ARGENTINE_PESO("ARS", Country.ARGENTINA),
    AUSTRALIAN_DOLLAR("AUD", Country.AUSTRALIA),
    BRAZILIAN_REAL("BRL", Country.BRAZIL),
    BRITISH_POUND("GBP", Country.GREAT_BRITAIN),
    CANADIAN_DOLLAR("CAD", Country.CANADA),
    CHILEAN_PESO("CLP", Country.CHILE),
    CHINESE_YUAN("CNY", Country.CHINA),
    COLOMBIAN_PESO("COP", Country.COLOMBIA),
    EURO("EUR", Country.EUROZONE),
    JAPANESE_YEN("JPY", Country.JAPAN),
    MEXICAN_PESO("MXN", Country.MEXICO),
    NEW_ZEALAND_DOLLAR("NZD", Country.NEW_ZEALAND),
    SWISS_FRANC("CHF", Country.SWITZERLAND),
    US_DOLLAR("USD", Country.UNITED_STATES_OF_AMERICA),
    VENEZUELAN_BOLIVAR("VEF", Country.VENEZUELA),
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
            case "AUD": return FiatCurrency.AUSTRALIAN_DOLLAR;
            case "BRL": return FiatCurrency.BRAZILIAN_REAL;
            case "GBP": return FiatCurrency.BRITISH_POUND;
            case "CAD": return FiatCurrency.CANADIAN_DOLLAR;
            case "CLP": return FiatCurrency.CHILEAN_PESO;
            case "CNY": return FiatCurrency.CHINESE_YUAN;
            case "COP": return FiatCurrency.COLOMBIAN_PESO;
            case "EUR": return FiatCurrency.EURO;
            case "JPY": return FiatCurrency.JAPANESE_YEN;
            case "MXN": return FiatCurrency.MEXICAN_PESO;
            case "NZD": return FiatCurrency.NEW_ZEALAND_DOLLAR;
            case "CHF": return FiatCurrency.SWISS_FRANC;
            case "USD": return FiatCurrency.US_DOLLAR;
            case "VEF": return FiatCurrency.VENEZUELAN_BOLIVAR;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the FiatCurrency enum");
        }
    }

    @Override
    public String getCode() { return this.code; }

}