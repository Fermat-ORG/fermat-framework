package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public enum BusinessTransactionStatus implements FermatEnum {
    PAUSED("PAU"),
    PENDING_PAYMENT("PEN"),
    COMPLETED("COM"),
    CANCELLED("CAN");

    private String code;

    BusinessTransactionStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static BusinessTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PAU":
                return BusinessTransactionStatus.PAUSED;
            case "PEN":
                return BusinessTransactionStatus.PENDING_PAYMENT;
            case "COM":
                return BusinessTransactionStatus.COMPLETED;
            case "CAN":
                return BusinessTransactionStatus.CANCELLED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the BusinessTransactionStatus enum");
        }
    }
}
