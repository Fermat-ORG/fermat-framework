package com.bitdubai.fermat_cbp_api.all_definition.enums;

import java.security.InvalidParameterException;

/**
 * Created by nelson on 12/12/15.
 */
public enum NegotiationStepStatus {

    /**
     * Tasa de cambio
     */
    ACCEPTED("ACCEPT"),
    /**
     * Cantidad a Vender
     */
    CHANGED("CHANGE"),
    /**
     * Metodo de pago que acepta el broker (Crypto, Cash in Hand, Cash Delivery, Bank)
     */
    CONFIRM("CONFIR");

    private final String code;

    NegotiationStepStatus(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NegotiationStepStatus getByCode(final String code) throws InvalidParameterException {
        switch (code) {
            case "ACCEPT":
                return ACCEPTED;
            case "CHANGE":
                return CHANGED;
            case "CONFIR":
                return CONFIRM;
            default:
                throw new InvalidParameterException("Code Received: " + code +
                        " - This Code Is Not Valid for the ClauseType enum");
        }
    }
}
