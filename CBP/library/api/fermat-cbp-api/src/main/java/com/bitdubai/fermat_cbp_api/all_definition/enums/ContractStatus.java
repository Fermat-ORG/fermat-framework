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
    NEGOTIATION("NEG"),
    PAUSED("PAU"),
    PENDING_CONFIRMATION("PEC"),
    PENDING_PAYMENT("PEN")

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
            case "NEG": return ContractStatus.NEGOTIATION;
            case "PAU": return ContractStatus.PAUSED;
            case "PEC": return ContractStatus.PENDING_CONFIRMATION;
            case "PEN": return ContractStatus.PENDING_PAYMENT;

            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
