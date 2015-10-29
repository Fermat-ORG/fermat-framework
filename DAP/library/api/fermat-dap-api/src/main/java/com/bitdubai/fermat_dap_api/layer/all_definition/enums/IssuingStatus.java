package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/10/15.
 */
public enum IssuingStatus {
    ISSUED("ISED"),
    ISSUING("ISSG"),
    UNEXPECTED_INTERRUPTION("UINT");

    private String code;

    IssuingStatus(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static IssuingStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "ISED":
                return IssuingStatus.ISSUED;
            case "ISSG":
                return IssuingStatus.ISSUING;
            case "UINT":
                return IssuingStatus.UNEXPECTED_INTERRUPTION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IssuingStatus enum.");
        }
    }
}
