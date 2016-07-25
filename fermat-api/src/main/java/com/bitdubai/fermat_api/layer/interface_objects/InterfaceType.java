package com.bitdubai.fermat_api.layer.interface_objects;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer on 2015.11.01..
 */
public enum InterfaceType {

    //Modified by Manuel Perez on 03/08/2015
    SUB_APP("SA"),
    WALLET("W"),
    EMPTY("E"),
    FOLDER("F"),
    P2P_APP("P2P");

    private String code;

    InterfaceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static InterfaceType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "SA":
                return SUB_APP;
            case "W":
                return WALLET;
            case "E":
                return EMPTY;
            case "F":
                return FOLDER;
            case "P2P":
                return P2P_APP;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the Developers enum");
        }
    }
}
