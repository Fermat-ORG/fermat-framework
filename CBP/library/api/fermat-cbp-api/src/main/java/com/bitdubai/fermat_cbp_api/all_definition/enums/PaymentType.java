package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 07/12/15.
 */
public enum PaymentType implements FermatEnum {
    CRYPTO_MONEY("CRYPTO"),
    FIAT_MONEY("FIAT");

    private String code;

    PaymentType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static PaymentType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CRYPTO":
                return PaymentType.CRYPTO_MONEY;
            case "FIAT":
                return PaymentType.FIAT_MONEY;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the PaymentType enum");
        }
    }
}
