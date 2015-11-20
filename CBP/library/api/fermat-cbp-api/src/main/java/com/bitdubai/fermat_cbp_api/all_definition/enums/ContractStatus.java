package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */
 
public enum ContractStatus implements FermatEnum {
    NEGOTIATION("NEG"),
    PAUSED("PAU"),
    PENDING_PAYMENT("PEN"),
    COMPLETED("COM"),
    CANCELLED ("CAN");

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
            case "NEG": return ContractStatus.NEGOTIATION;
            case "PAU": return ContractStatus.PAUSED;
            case "PEN": return ContractStatus.PENDING_PAYMENT;
            case "COM": return ContractStatus.COMPLETED;
            case "CAN": return ContractStatus.CANCELLED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
