package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.Objects;

/**
 * Created by eze on 09/06/15.
 */
public enum Specialist implements FermatEnum {
    ASSET_ISSUER_SPECIALIST("AIS"),
    ASSET_USER_SPECIALIST("AUS"),
    CRYPTO_ROUTER_SPECIALIST("CPR"),
    EXTRA_USER_SPECIALIST("EXU"),
    DEVICE_USER_SPECIALIST("DVU"),
    INTRA_USER_SPECIALIST("INU"),
    UNKNOWN_SPECIALIST("UNK");

    private final String code;

    Specialist(String Code) {
        this.code = Code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static Specialist getByCode(String code) throws InvalidParameterException {
        for (Specialist s : values()) {
            if (Objects.equals(s.getCode(), code)) {
                return s;
            }
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Specialist enum");
    }

}

