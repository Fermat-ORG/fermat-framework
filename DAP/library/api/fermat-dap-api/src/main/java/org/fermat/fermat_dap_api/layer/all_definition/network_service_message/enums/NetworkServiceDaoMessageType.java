package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 10/8/15.
 */
public enum NetworkServiceDaoMessageType {
    ACTOR_ISSUER("AISS"),
    ACTOR_USER("AUSER"),
    ACTOR_REDEEM_POINT("ARPT"),
    DIGITAL_ASSET_METADATA("DAMD");

    String code;

    NetworkServiceDaoMessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public NetworkServiceDaoMessageType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "AISS":
                return NetworkServiceDaoMessageType.ACTOR_ISSUER;
            case "AUSER":
                return NetworkServiceDaoMessageType.ACTOR_USER;
            case "ARPT":
                return NetworkServiceDaoMessageType.ACTOR_REDEEM_POINT;
            case "DAMD":
                return NetworkServiceDaoMessageType.DIGITAL_ASSET_METADATA;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AssetCreditType enum.");
        }
    }
}