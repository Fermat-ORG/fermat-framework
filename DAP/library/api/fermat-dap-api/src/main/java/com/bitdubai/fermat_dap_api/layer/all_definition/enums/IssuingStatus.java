package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/10/15.
 */
public enum IssuingStatus implements FermatEnum {
    ACTOR_ISSUER_NULL ("AIN"),
    ASSET_ALREADY_ISSUED("AAIS"),
    DATABASE_EXCEPTION("DAEX"),
    DIGITAL_ASSET_INCOMPLETE("DAIN"),
    FILESYSTEM_EXCEPTION("FIEX"),
    INSUFFICIENT_FONDS("ISFF"),
    INTRA_ACTOR_NULL("IAN"),
    ISSUED("ISED"),
    ISSUING("ISSG"),
    INVALID_ADDRESS_TO_SEND("IATS"),
    INVALID_NUMBER_TO_ISSUE("INTI"),
    TRANSACTION_ALREADY_SENT("TRAS"),
    UNEXPECTED_INTERRUPTION("UINT"),
    WALLET_EXCEPTION("WAEX");

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
