package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public enum MessageTypes implements FermatEnum {

    CONNECTION_INFORMATION("INF"),
    CONNECTION_REQUEST("REQ"),;

    private String code;

    MessageTypes(String code) {
        this.code = code;
    }

    public static MessageTypes getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "INF":
                return CONNECTION_INFORMATION;
            case "REQ":
                return CONNECTION_REQUEST;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the MessageTypes enum."
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}
