package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public enum CryptoCurrencyType implements FermatEnum {
    BITCOIN("BTC");

    private String code;

    CryptoCurrencyType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CryptoCurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "BTC":
                return CryptoCurrencyType.BITCOIN;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the CryptoCurrencyType enum");
        }
    }
}
