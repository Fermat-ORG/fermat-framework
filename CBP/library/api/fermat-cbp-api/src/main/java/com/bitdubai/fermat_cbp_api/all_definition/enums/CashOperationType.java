package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public enum CashOperationType implements FermatEnum {
    ON_HAND("ONH"),
    DELIVERY("DEL");

    private String code;

    CashOperationType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CashOperationType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "COH":
                return CashOperationType.ON_HAND;
            case "CAD":
                return CashOperationType.DELIVERY;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the CashCurrencyType enum");
        }
    }
}
