package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum CashCurrencyType {
    DOLARUSA("USD"),
    EURO("EUR"),
    DOLARAUSTRALIANO("AUD"),
    DOLARCANADIENCE("CAD"),
    FRANCOSUIZP("CHF"),
    LIBRAESTERLINA("GBP"),
    YENJAPONES("JPY"),
    BOLIVARES("BS");

    private String code;

    CashCurrencyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CashCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "USD": return CashCurrencyType.DOLARUSA;
            case "EUR": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.EURO;
            case "AUD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLARAUSTRALIANO;
            case "CAD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLARCANADIENCE;
            case "CHF": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.FRANCOSUIZP;
            case "GBP": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.LIBRAESTERLINA;
            case "JPY": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.YENJAPONES;
            case "BS": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.BOLIVARES;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CashCurrencyType enum");
        }
    }
}
