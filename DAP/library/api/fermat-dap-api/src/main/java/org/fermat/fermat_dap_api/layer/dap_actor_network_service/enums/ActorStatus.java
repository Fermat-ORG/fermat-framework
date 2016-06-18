package org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum ActorStatus implements FermatEnum {

    LOGGED_IN("LOGDIN"),
    LOGGED_OUT("LOGOUT"),
    UNKNOWN("UNKNOW"),;

    private final String code;

    ActorStatus(final String code) {
        this.code = code;
    }

    public static ActorStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "LOGDIN":
                return LOGGED_IN;
            case "LOGOUT":
                return LOGGED_OUT;
            case "UNKNOW":
                return UNKNOWN;
            default:
                throw new InvalidParameterException(
                        "Code received: " + code,
                        "The code received is not valid for the IntraUserStatus enum."
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
