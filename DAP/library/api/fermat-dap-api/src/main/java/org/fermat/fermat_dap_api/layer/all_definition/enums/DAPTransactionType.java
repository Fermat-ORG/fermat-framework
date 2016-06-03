package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/10/15.
 */
public enum DAPTransactionType {
    DISTRIBUTION("DX"),
    ISSUING("TX"),
    RECEPTION("RX");

    private String code;

    DAPTransactionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public DAPTransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DS":
                return DAPTransactionType.DISTRIBUTION;
            case "TX":
                return DAPTransactionType.ISSUING;
            case "RX":
                return DAPTransactionType.RECEPTION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DAPTransactionType enum.");
        }
    }
}
