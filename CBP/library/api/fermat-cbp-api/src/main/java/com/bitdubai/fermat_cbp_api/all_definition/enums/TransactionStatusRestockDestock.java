package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 16/11/15.
 * Modified by abicelis
 */
public enum TransactionStatusRestockDestock implements FermatEnum {

    INIT_TRANSACTION("ITRANS"),
    IN_HOLD("IHOLD"),
    IN_UNHOLD("IUNHOLD"),
    IN_WALLET("IWALLET"),
    COMPLETED("COMPLETED"),
    IN_EXECUTION("IEJEC"),
    REJECTED("REJECTED");

    TransactionStatusRestockDestock(String code) {
        this.code = code;
    }

    private String code;

    @Override
    public String getCode() {
        return this.code;
    }

    public static TransactionStatusRestockDestock getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ITRANS":
                return TransactionStatusRestockDestock.INIT_TRANSACTION;
            case "IHOLD":
                return TransactionStatusRestockDestock.IN_HOLD;
            case "IUNHOLD":
                return TransactionStatusRestockDestock.IN_UNHOLD;
            case "IWALLET":
                return TransactionStatusRestockDestock.IN_WALLET;
            case "IEJEC":
                return TransactionStatusRestockDestock.IN_EXECUTION;
            case "COMPLETED":
                return TransactionStatusRestockDestock.COMPLETED;
            case "REJECTED":
                return TransactionStatusRestockDestock.REJECTED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the TransactionStatusRestockDestock enum");
        }
    }
}
