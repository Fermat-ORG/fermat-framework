package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 27/11/15.
 */
public enum OriginTransaction implements FermatEnum {
    STOCK_INITIAL("SINITIAL");

    OriginTransaction(String code) {
        this.code = code;
    }

    private String code;

    @Override
    public String getCode() {
        return this.code;
    }

    public static OriginTransaction getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "SINITIAL":    return OriginTransaction.STOCK_INITIAL;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
