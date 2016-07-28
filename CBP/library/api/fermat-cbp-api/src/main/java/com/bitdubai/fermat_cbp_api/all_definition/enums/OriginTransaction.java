package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by franklin on 27/11/15.
 */
public enum OriginTransaction implements FermatEnum {
    STOCK_INITIAL("SINITIAL"),
    RESTOCK_AUTOMATIC("RAUTOMATIC"),
    RESTOCK("RESTOCK"),
    DESTOCK("DESTOCK"),
    PURCHASE("PURC"),
    SALE("SALE"),
    EARNING_EXTRACTION("EAREXT");

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
            case "SINITIAL":
                return STOCK_INITIAL;
            case "RAUTOMATIC":
                return RESTOCK_AUTOMATIC;
            case "RESTOCK":
                return RESTOCK;
            case "DESTOCK":
                return DESTOCK;
            case "SALE":
                return SALE;
            case "PURC":
                return PURCHASE;
            case "EAREXT":
                return EARNING_EXTRACTION;
            default:
                throw new InvalidParameterException(new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the OriginTransaction enum");
        }
    }
}
