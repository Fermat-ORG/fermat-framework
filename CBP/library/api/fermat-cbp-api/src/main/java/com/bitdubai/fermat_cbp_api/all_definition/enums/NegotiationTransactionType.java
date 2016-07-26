package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public enum NegotiationTransactionType implements FermatEnum {
    CUSTOMER_BROKER_NEW("CBN"),
    CUSTOMER_BROKER_UPDATE("CBU"),
    CUSTOMER_BROKER_CLOSE("CBC");

    private String code;

    NegotiationTransactionType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static NegotiationTransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CBN":
                return NegotiationTransactionType.CUSTOMER_BROKER_NEW;
            case "CBU":
                return NegotiationTransactionType.CUSTOMER_BROKER_UPDATE;
            case "CBC":
                return NegotiationTransactionType.CUSTOMER_BROKER_CLOSE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the NegotiationTransactionType enum");
        }
    }
}
