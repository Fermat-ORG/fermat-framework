package com.bitdubai.fermat_bnk_api.all_definition.enums;

import com.bitdubai.fermat_bnk_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum BankCurrencyType {
    DOLARUSA("USD"),
    EURO("EUR"),
    DOLARAUSTRALIANO("AUD"),
    DOLARCANADIENCE("CAD"),
    FRANCOSUIZO("CHF"),
    LIBRAESTERLINA("GBP"),
    YENJAPONES("JPY"),
    BOLIVARES("VEF");

    private String code;

    BankCurrencyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BankCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "USD": return BankCurrencyType.DOLARUSA;
            case "EUR": return BankCurrencyType.EURO;
            case "AUD": return BankCurrencyType.DOLARAUSTRALIANO;
            case "CAD": return BankCurrencyType.DOLARCANADIENCE;
            case "CHF": return BankCurrencyType.FRANCOSUIZO;
            case "GBP": return BankCurrencyType.LIBRAESTERLINA;
            case "JPY": return BankCurrencyType.YENJAPONES;
            case "VEF": return BankCurrencyType.BOLIVARES;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankCurrencyType enum");
        }
    }
}
