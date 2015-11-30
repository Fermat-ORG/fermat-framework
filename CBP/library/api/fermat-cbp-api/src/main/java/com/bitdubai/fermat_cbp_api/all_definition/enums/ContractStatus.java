package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 23/11/2015
 */

public enum ContractStatus implements FermatEnum {
    CANCELLED ("CAN"),
    COMPLETED("COM"),
    MERCHANDISE_SUBMIT("MES"),
    PAUSED("PSD"),
    PENDING_MERCHANDISE("PEM"),
    PENDING_PAYMENT("PEN"),
    PAYMENT_SUBMIT("PYS"),
    ;

    private String code;

    ContractStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ContractStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CAN": return ContractStatus.CANCELLED;
            case "COM": return ContractStatus.COMPLETED;
            case "MES": return ContractStatus.MERCHANDISE_SUBMIT;
            case "PSD": return ContractStatus.PAUSED;
            case "PEM": return ContractStatus.PENDING_MERCHANDISE;
            case "PEN": return ContractStatus.PENDING_PAYMENT;
            case "PYS": return ContractStatus.PAYMENT_SUBMIT;

            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
