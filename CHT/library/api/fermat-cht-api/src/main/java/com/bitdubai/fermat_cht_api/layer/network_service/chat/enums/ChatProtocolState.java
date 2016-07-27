package com.bitdubai.fermat_cht_api.layer.network_service.chat.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer- (matiasfurszyfer@gmail.com) on 30/09/2015.
 */
public enum ChatProtocolState implements FermatEnum {

    DONE("DON"), // final state of request.
    PENDING_ACTION("PEA"), // pending local action, is given after raise a crypto payment request event.
    PROCESSING_RECEIVE("PCR"), // when an action from the network service is needed receiving.
    PROCESSING_SEND("PCS"), // when an action from the network service is needed sending.
    WAITING_RESPONSE("WRE"),  // waiting response from the counterpart.
    SENT("SENT"),
    DELIVERY("DELIVERY");

    private String code;

    ChatProtocolState(String code) {
        this.code = code;
    }

    public static ChatProtocolState getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "DON":
                return DONE;
            case "PEA":
                return PENDING_ACTION;
            case "PCR":
                return PROCESSING_RECEIVE;
            case "PCS":
                return PROCESSING_SEND;
            case "WRE":
                return WAITING_RESPONSE;
            case "SENT":
                return SENT;
            case "DELIVERY":
                return DELIVERY;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
