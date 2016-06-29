package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Nerio on 24/11/15.
 */
public enum NetworkServiceMessageType implements FermatEnum {

    ACCEPT("ACPT"),
    DENY("DENY"),
    REQUEST("RQST"),;

    private String code;

    NetworkServiceMessageType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public NetworkServiceMessageType getByCode(String code) throws InvalidParameterException {
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
