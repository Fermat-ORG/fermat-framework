package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by mati on 2016.02.04..
 */
public enum FermatAppType implements FermatEnum {

    WALLET("W"),
    SUB_APP("SA"),
    DESKTOP("D"),
    P2P_APP("P2P_APP");

    private String code;

    FermatAppType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return code;
    }

    public static FermatAppType getValueFromString(String code) throws InvalidParameterException {
        switch (code) {
            case "W":
                return WALLET;
            case "SA":
                return SUB_APP;
            case "D":
                return DESKTOP;
            case "P2P_APP":
                return P2P_APP;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the Plugins enum");

        }
        //return null;
    }

    public static int size() {
        return values().length;
    }

}
