package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public enum NegotiationTransmissionType implements FermatEnum {
    TRANSMISSION_NEGOTIATION("TRN"),
    TRANSMISSION_CONFIRM("TRC");

    private String code;

    NegotiationTransmissionType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationTransmissionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "TRN":
                return NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            case "TRC":
                return NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the NegotiationTransmissionType enum");
        }
    }
}
