package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
 
public enum CashCurrencyType implements FermatEnum {
    DOLAR_USA("USD"),
    EURO("EUR"),
    DOLAR_AUSTRALIANO("AUD"),
    DOLAR_CANADIENCE("CAD"),
    FRANCO_SUIZO("CHF"),
    LIBRA_ESTERLINA("GBP"),
    YEN_JAPONES("JPY"),
    BOLIVARES("BS");

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
            case "USD": return CashCurrencyType.DOLAR_USA;
            case "EUR": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.EURO;
            case "AUD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLAR_AUSTRALIANO;
            case "CAD": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.DOLAR_CANADIENCE;
            case "CHF": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.FRANCO_SUIZO;
            case "GBP": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.LIBRA_ESTERLINA;
            case "JPY": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.YEN_JAPONES;
            case "BS": return com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType.BOLIVARES;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CashCurrencyType enum");
        }
    }
}
