package org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 *
 */
public enum ActorAssetProtocolState implements FermatEnum {

    DONE("DONE"), // final state of request.
    PENDING_ACTION("PACT"), // pending local action, request event.
    PROCESSING_RECEIVE("PREV"), // when an action from the network service is needed receiving.
    PROCESSING_SEND("PSND"), // when an action from the network service is needed sending.
    WAITING_RESPONSE("WRES"),  // waiting response from the counterpart.
    SENT("SENT"),
    DELIVERY("DELY"),;

    private String code;

    ActorAssetProtocolState(String code) {
        this.code = code;
    }

    public static ActorAssetProtocolState getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "DONE":
                return DONE;
            case "PACT":
                return PENDING_ACTION;
            case "PREV":
                return PROCESSING_RECEIVE;
            case "PSND":
                return PROCESSING_SEND;
            case "WRES":
                return WAITING_RESPONSE;
            case "SENT":
                return SENT;
            case "DELY":
                return DELIVERY;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the RequestProtocolState enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
