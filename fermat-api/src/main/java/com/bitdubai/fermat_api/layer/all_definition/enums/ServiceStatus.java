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
    STOPPED("STOPPED");

    public String code;

    ServiceStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ServiceStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CREATED": return ServiceStatus.CREATED;
            case "STARTED": return ServiceStatus.STARTED;
            case "PAUSED":  return ServiceStatus.PAUSED;
            case "STOPPED": return ServiceStatus.STOPPED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ServiceStatus enum");
        }
    }
}
