package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Alex on 11/2/2015.
 */

public enum FiatCurrencyType implements FermatEnum {

    VENEZUELAN_BOLIVAR("VEF"),
    ARGENTINE_PESO("ARS"),
    BRAZILIAN_REAL("BRL"),
    CHILEAN_PESO("CLP"),
    COLOMBIAN_PESO("COP"),
    MEXICAN_PESO("MXN"),
    US_DOLLAR("USD"),
    CANADIAN_DOLLAR("CAD"),
    EURO("EUR"),
    BRITISH_POUND("GBP"),
    SWISS_FRANC("CHF"),
    NEW_ZEALAND_DOLLAR("NZD"),
    AUSTRALIAN_DOLLAR("AUD"),
    JAPANESE_YEN("JPY"),
    CHINESE_YUAN("CNY"),
    BITCOIN("BTC");

    private String code;

    FiatCurrencyType(String code) { this.code = code; }

    @Override
    public String getCode() { return this.code; }

    public static FiatCurrencyType getFiatCurrencyTypeByCode(String currencyCode) throws InvalidParameterException {
        switch(currencyCode)
        {
            case "VEF": return VENEZUELAN_BOLIVAR;
            case "ARS": return ARGENTINE_PESO;
            case "BRL": return BRAZILIAN_REAL;
            case "CLP": return CHILEAN_PESO;
            case "COP": return COLOMBIAN_PESO;
            case "MXN": return MEXICAN_PESO;
            case "USD": return US_DOLLAR;
            case "CAD": return CANADIAN_DOLLAR;
            case "EUR": return EURO;
            case "GBP": return BRITISH_POUND;
            case "CHF": return SWISS_FRANC;
            case "NZD": return NEW_ZEALAND_DOLLAR;
            case "AUD": return AUSTRALIAN_DOLLAR;
            case "JPY": return JAPANESE_YEN;
            case "CNY": return CHINESE_YUAN;
            case "BTC": return BITCOIN;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null,
                        "Received currencyCode: " + currencyCode, "This is an invalid FiatCurrencyType code");

        }
    }
}
