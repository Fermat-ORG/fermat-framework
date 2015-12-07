package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 30.11.15.
 */
public enum NegotiationTransmissionState implements FermatEnum {

    DONE                                ("DON"), // final state of request.
    PENDING_ACTION                      ("PEA"), // pending local action, is given after raise a crypto addresses event.
    PROCESSING_SEND                     ("PCS"), // when an action from the network service is needed sending.
    WAITING_RESPONSE                    ("WRE"), // waiting response from the counterpart.

    SEEN_BY_DESTINATION_NETWORK_SERVICE ("SBDNS"),
    SEEN_BY_OWN_NETWORK_SERVICE         ("SBONS"),
    CONFIRM_RESPONSE                    ("CFR"),
    CONFIRM_NEGOTIATION                 ("CFN"),
    SENT                                ("SENT"),

    ;
    /*
    CONFIRM_CONTRACT("CCX"),
    CONFIRM_RESPONSE("CRX"),
    PRE_PROCESSING_SEND("PPS"),
    SEEN_BY_DESTINATION_NETWORK_SERVICE("SBDNS"),
    SEEN_BY_OWN_NETWORK_SERVICE("SBONS"),
    SENT("SENT"),
    SENDING_HASH("HTX"),
    UPDATE_CONTRACT("UCX");
    */

    private String code;

    NegotiationTransmissionState(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationTransmissionState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DON"      : return DONE                               ;
            case "PEA"      : return PENDING_ACTION                     ;
            case "PCS"      : return PROCESSING_SEND                    ;
            case "WRE"      : return WAITING_RESPONSE                   ;
            case "SBDNS"    : return SEEN_BY_DESTINATION_NETWORK_SERVICE;
            case "SBONS"    : return SEEN_BY_OWN_NETWORK_SERVICE        ;
            case "CFR"      : return CONFIRM_RESPONSE                   ;
            case "CFN"      : return CONFIRM_NEGOTIATION                ;
            case "SENT"     : return SENT                               ;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NegotiationTransmissionState enum");
        }
    }

}
