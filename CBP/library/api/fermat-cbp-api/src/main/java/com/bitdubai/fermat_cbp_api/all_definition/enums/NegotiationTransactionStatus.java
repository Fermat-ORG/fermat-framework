package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 09.12.15.
 */
public enum NegotiationTransactionStatus implements FermatEnum {
    PENDING_CONFIRMATION("PEC"),
    PENDING_RESPONSE("PER"),
    PENDING_SUBMIT("PES"),
    PENDING_SUBMIT_CONFIRM("PSC"),
    SENDING_NEGOTIATION("SDN"),
    CONFIRM_NEGOTIATION("CFN"),
    REJECTED_NEGOTIATION("REN");

    private String code;

    NegotiationTransactionStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationTransactionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "PEC":
                return NegotiationTransactionStatus.PENDING_CONFIRMATION;
            case "PER":
                return NegotiationTransactionStatus.PENDING_RESPONSE;
            case "PES":
                return NegotiationTransactionStatus.PENDING_SUBMIT;
            case "PSC":
                return NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM;
            case "SDN":
                return NegotiationTransactionStatus.SENDING_NEGOTIATION;
            case "CFN":
                return NegotiationTransactionStatus.CONFIRM_NEGOTIATION;
            case "REN":
                return NegotiationTransactionStatus.REJECTED_NEGOTIATION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the NegotiationTransactionStatus enum");
        }
    }
}
