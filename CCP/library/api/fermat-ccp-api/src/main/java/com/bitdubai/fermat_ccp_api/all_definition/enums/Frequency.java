package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by Andres Abreu on 17/06/16.
 */



public enum Frequency implements FermatEnum{

    LOW ("low"),
    NORMAL("normal"),
    HIGH("high"),
    NONE("none")
    ;


    private final String code;

    Frequency(String code) {
        this.code = code;
    }

    public static Frequency getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "low":return LOW;
            case "normal":return NORMAL;
            case "high":return HIGH;
            case "none":return NONE;

            default:
                throw new InvalidParameterException(
                        "Frequency Value: " + code,
                        "This is an invalid Frequency"
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }

    }

