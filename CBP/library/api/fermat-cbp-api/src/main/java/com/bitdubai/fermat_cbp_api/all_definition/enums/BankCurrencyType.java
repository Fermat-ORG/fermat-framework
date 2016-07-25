package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public enum BankCurrencyType implements FermatEnum {
    DOLAR_USA("USD"),
    EURO("EUR"),
    DOLAR_AUSTRALIANO("AUD"),
    DOLAR_CANADIENCE("CAD"),
    FRANCO_SUIZO("CHF"),
    LIBRA_ESTERLINA("GBP"),
    YEN_JAPONES("JPY"),
    BOLIVARES("BS");

    private String code;

    BankCurrencyType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static BankCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "USD":
                return DOLAR_USA;
            case "EUR":
                return EURO;
            case "AUD":
                return DOLAR_AUSTRALIANO;
            case "CAD":
                return DOLAR_CANADIENCE;
            case "CHF":
                return FRANCO_SUIZO;
            case "GBP":
                return LIBRA_ESTERLINA;
            case "JPY":
                return YEN_JAPONES;
            case "BS":
                return BOLIVARES;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the BankCurrencyType enum");
        }
    }
}
