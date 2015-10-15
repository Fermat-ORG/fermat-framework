package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/10/15.
 */
public enum DAPMessageType {
    DIGITAL_ASSET_METADATA("DAM"),
    INCOMING("RX"),
    OUTGOING("TX");

    private String code;

    DAPMessageType(String code){
        this.code=code;
    }

    public String getCode() { return this.code ; }

    public DAPMessageType getByCode(String code)throws InvalidParameterException {
        switch (code){
            case "DAM":
                return DAPMessageType.DIGITAL_ASSET_METADATA;
            case "RX":
                return DAPMessageType.INCOMING;
            case "TX":
                return DAPMessageType.OUTGOING;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DAPMessageType enum.");
        }
    }

}
