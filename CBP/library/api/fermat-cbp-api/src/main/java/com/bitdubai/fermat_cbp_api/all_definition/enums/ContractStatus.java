package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 23/11/2015
 */
 
public enum ContractStatus implements FermatEnum {
    OPEN ("CAN"),
    EXECUTED("CRC"),
    CANCELLED("COM")
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
            case "OPE": return ContractStatus.OPEN;
            case "EXE": return ContractStatus.EXECUTED;
            case "CAN": return ContractStatus.CANCELLED;

            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
