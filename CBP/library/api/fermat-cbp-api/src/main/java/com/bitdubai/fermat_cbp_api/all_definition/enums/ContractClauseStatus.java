package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by jorge on 12-10-2015.
 * Update by Angel on 28/11/2015
 */

public enum ContractClauseStatus implements FermatEnum {
    PENDING("PEN"),
    QUEUED("QUE"),
    INPROGRESS("INP"),
    EXECUTED("EXE");

    private String code;

    ContractClauseStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ContractClauseStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PEN":
                return PENDING;
            case "QUE":
                return QUEUED;
            case "INP":
                return INPROGRESS;
            case "EXE":
                return EXECUTED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ClauseStatus enum");
        }
    }
}
