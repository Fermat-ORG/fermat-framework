package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 10/8/15.
 */
public enum NetworkServiceDaoMessageType implements FermatEnum {

    ACCEPT       ("ACPT"),
    DENY         ("DENY"),
    REQUEST      ("RQST"),
    ;

    private String code;

    NetworkServiceDaoMessageType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public NetworkServiceDaoMessageType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ACPT":
                return ACCEPT;
            case "DENY":
                return DENY;
            case "RQST":
                return REQUEST;
            default:
                throw new InvalidParameterException(
                        InvalidParameterException.DEFAULT_MESSAGE,
                        null,
                        "Code Received: " + code,
                        "This Code Is Not Valid for the NetworkServiceDaoMessageType Enum.");
        }
    }
}
