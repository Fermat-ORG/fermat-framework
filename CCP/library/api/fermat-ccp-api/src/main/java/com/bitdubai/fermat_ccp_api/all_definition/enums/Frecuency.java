package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by Andres Abreu on 17/06/16.
 */



public enum Frecuency  implements FermatEnum{

    LOW ("low"),
    NORMAL("normal"),
    HIGH("high"),
    NONE("none")
    ;


    private final String code;

    Frecuency(String code) {
        this.code = code;
    }

    public static Frecuency getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "low":return LOW;
            case "normal":return NORMAL;
            case "high":return HIGH;
            case "none":return NONE;

            default:
                throw new InvalidParameterException(
                        "Frecuency Value: " + code,
                        "This is an invalid Frecuency"
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }

    }

