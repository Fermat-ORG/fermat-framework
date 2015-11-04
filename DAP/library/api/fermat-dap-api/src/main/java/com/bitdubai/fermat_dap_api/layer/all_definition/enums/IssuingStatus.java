package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/10/15.
 */
public enum IssuingStatus implements FermatEnum {
    ISSUING("ISSG"),
    ASSET_ALREADY_ISSUED("AAIS"),
    INSUFFICIENT_FONDS("ISFF"),
    INVALID_ADDRESS_TO_SEND("IATS"),
    WALLET_EXCEPTION("WAEX"),
    DATABASE_EXCEPTION("DAEX"),
    FILESYSTEM_EXCEPTION("FIEX"),
    DIGITAL_ASSET_INCOMPLETE("DAIN"),
    TRANSACTION_ALREADY_SENT("TRAS"),
    INVALID_NUMBER_TO_ISSUE("INTI"),
    UNEXPECTED_INTERRUPTION("UINT"),
    ISSUED("ISED");

    private String code;

    IssuingStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static IssuingStatus getByCode(String code) throws InvalidParameterException {
        for (IssuingStatus status : values()) {
            if (status.code.equals(code)) return status;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IssuingStatus enum.");
    }
}
