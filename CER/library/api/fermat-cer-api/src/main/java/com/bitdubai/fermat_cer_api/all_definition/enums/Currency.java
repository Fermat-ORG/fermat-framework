package com.bitdubai.fermat_cer_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Country;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public enum Currency implements FermatEnum {
    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ARGENTINE_PESO("ARS", Country.ARGENTINA),
    AUSTRALIAN_DOLLAR("AUD", Country.AUSTRALIA),
    BRAZILIAN_REAL("BRL", Country.BRAZIL),
    BITCOIN("BTC", Country.NONE),
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

    Currency(final String code, final Country country) {
        this.code = code;
        this.country = country;
    }

    public Country getCountry() {
        return this.country;
    }

    public static Currency getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ARS": return Currency.ARGENTINE_PESO;
            case "AUD": return Currency.AUSTRALIAN_DOLLAR;
            case "BRL": return Currency.BRAZILIAN_REAL;
            case "BTC": return Currency.BITCOIN;
            case "GBP": return Currency.BRITISH_POUND;
            case "CAD": return Currency.CANADIAN_DOLLAR;
            case "CLP": return Currency.CHILEAN_PESO;
            case "CNY": return Currency.CHINESE_YUAN;
            case "COP": return Currency.COLOMBIAN_PESO;
            case "EUR": return Currency.EURO;
            case "JPY": return Currency.JAPANESE_YEN;
            case "MXN": return Currency.MEXICAN_PESO;
            case "NZD": return Currency.NEW_ZEALAND_DOLLAR;
            case "CHF": return Currency.SWISS_FRANC;
            case "USD": return Currency.US_DOLLAR;
            case "VEF": return Currency.VENEZUELAN_BOLIVAR;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The received code is not valid for the Currency enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
