package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 25.01.15.
 */
public enum ServiceStatus implements FermatEnum {
    //Modified by Manuel Perez on 03/08/2015
    CREATED("CREATED"),
    STARTED("STARTED"),
    PAUSED("PAUSED"),
    STOPPED("STOPPED"),

    STARTING("STARTING"),
    ERROR("ERROR"),;

    public String code;

    ServiceStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ServiceStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "CREATED":
                return CREATED;
            case "STARTED":
                return STARTED;
            case "PAUSED":
                return PAUSED;
            case "STOPPED":
                return STOPPED;

            case "ERROR":
                return ERROR;
            case "STARTING":
                return STARTING;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the ServiceStatus enum"
                );
        }
    }
}
