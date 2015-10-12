package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum BankCurrencyType {
    DOLARUSA("USD"),
    EURO("EUR"),
    DOLARAUSTRALIANO("AUD"),
    DOLARCANADIENCE("CAD"),
    FRANCOSUIZP("CHF"),
    LIBRAESTERLINA("GBP"),
    YENJAPONES("JPY"),
    BOLIVARES("BS");

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
            case "EUR": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.EURO;
            case "AUD": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.DOLARAUSTRALIANO;
            case "CAD": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.DOLARCANADIENCE;
            case "CHF": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.FRANCOSUIZP;
            case "GBP": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.LIBRAESTERLINA;
            case "JPY": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.YENJAPONES;
            case "BS": return com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType.BOLIVARES;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BankCurrencyType enum");
        }
    }
}
