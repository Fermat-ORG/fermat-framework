package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by natalia on 17/09/15.
 */
public enum CustomerType implements FermatEnum {
    SPORADIC("SPO"),
    FREQUENT("FRQ");

    private String code;

    CustomerType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CustomerType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "SPO":
                return CustomerType.SPORADIC;
            case "FRQ":
                return CustomerType.FREQUENT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ContactState enum");
        }
    }
}

