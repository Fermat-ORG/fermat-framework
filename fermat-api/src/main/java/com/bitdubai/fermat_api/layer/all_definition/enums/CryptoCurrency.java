package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 20.12.14.
 */
public enum CryptoCurrency implements FermatEnum {

    BITCOIN("BTC"),
    CHAVEZCOIN("CHC"),
    LITECOIN("LTC");

    private String code;

    CryptoCurrency(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CryptoCurrency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BTC": return CryptoCurrency.BITCOIN;
            case "CHC": return CryptoCurrency.CHAVEZCOIN;
            case "LTC": return CryptoCurrency.LITECOIN;
            //Modified by Manuel Perez on 03/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CryptoCurrency enum");
        }
        /**
         * Return by default.
         */
        //return CryptoCurrency.BITCOIN;
    }
}