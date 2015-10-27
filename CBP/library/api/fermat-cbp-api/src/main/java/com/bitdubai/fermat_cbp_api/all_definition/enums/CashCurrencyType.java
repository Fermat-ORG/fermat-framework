package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum CashCurrencyType implements FermatEnum {
    DOLAR_USA("USD"),
    EURO("EUR"),
<<<<<<< HEAD
    DOLARAUSTRALIANO("AUD"),
    DOLARCANADIENCE("CAD"),
    FRANCOSUIZO("CHF"),
    LIBRAESTERLINA("GBP"),
    YENJAPONES("JPY"),
    BOLIVAR("BS");
=======
    DOLAR_AUSTRALIANO("AUD"),
    DOLAR_CANADIENCE("CAD"),
    FRANCO_SUIZO("CHF"),
    LIBRA_ESTERLINA("GBP"),
    YEN_JAPONES("JPY"),
    BOLIVARES("BS");
>>>>>>> 0199ca4ad9bf94d2b852104e4e335cb2cc4b0060

    private String code;

    CashCurrencyType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CashCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
<<<<<<< HEAD
            case "USD": return CashCurrencyType.DOLARUSA;
            case "EUR": return CashCurrencyType.EURO;
            case "AUD": return CashCurrencyType.DOLARAUSTRALIANO;
            case "CAD": return CashCurrencyType.DOLARCANADIENCE;
            case "CHF": return CashCurrencyType.FRANCOSUIZO;
            case "GBP": return CashCurrencyType.LIBRAESTERLINA;
            case "JPY": return CashCurrencyType.YENJAPONES;
            case "BS": return CashCurrencyType.BOLIVAR;
=======
            case "USD": return CashCurrencyType.DOLAR_USA;
            case "EUR": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.EURO;
            case "AUD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLAR_AUSTRALIANO;
            case "CAD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLAR_CANADIENCE;
            case "CHF": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.FRANCO_SUIZO;
            case "GBP": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.LIBRA_ESTERLINA;
            case "JPY": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.YEN_JAPONES;
            case "BS": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.BOLIVARES;
>>>>>>> 0199ca4ad9bf94d2b852104e4e335cb2cc4b0060
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CashCurrencyType enum");
        }
    }
}
