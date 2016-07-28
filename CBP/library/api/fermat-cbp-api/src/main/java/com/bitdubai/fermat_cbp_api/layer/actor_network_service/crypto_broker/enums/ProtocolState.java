package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState</code>
 * represents the protocol state of the crypto addresses exchange request in the network service.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public enum ProtocolState implements FermatEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    DONE("DON"), // final state of request.
    PENDING_LOCAL_ACTION("PLA"), // pending local action
    PENDING_REMOTE_ACTION("PRA"), // waiting response from the counterpart
    PROCESSING_RECEIVE("PCR"), // when an action from the network service is needed receiving.
    PROCESSING_SEND("PCS"), // when an action from the network service is needed sending.
    WAITING_RECEIPT_CONFIRMATION("WRC"), // the ns waits for the receipt confirmation of the counterpart.

    ;

    private final String code;

    ProtocolState(final String code) {
        this.code = code;
    }

    public static ProtocolState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "DON":
                return DONE;
            case "PLA":
                return PENDING_LOCAL_ACTION;
            case "PRA":
                return PENDING_REMOTE_ACTION;
            case "PCR":
                return PROCESSING_RECEIVE;
            case "PCS":
                return PROCESSING_SEND;
            case "WRC":
                return WAITING_RECEIPT_CONFIRMATION;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the ProtocolState enum"
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
