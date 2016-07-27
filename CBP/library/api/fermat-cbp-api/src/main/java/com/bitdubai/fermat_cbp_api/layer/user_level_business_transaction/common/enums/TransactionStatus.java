package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 14/12/15.
 */
public enum TransactionStatus implements FermatEnum {

    IN_PROCESS("IPRO"),
    IN_OPEN_CONTRACT("IOCO"),
    IN_CONTRACT_SUBMIT("ICSU"),
    IN_PAYMENT_SUBMIT("IPSU"),
    IN_MERCHANDISE_SUBMIT("IMSU"),
    IN_PENDING_MERCHANDISE("IPME"),
    COMPLETED("COMP"),
    CANCELLED("CANC");

    String code;

    TransactionStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static TransactionStatus getByCode(String code) throws InvalidParameterException {
        for (TransactionStatus value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the OpenContractStatus enum.");
    }

    @Override
    public String toString() {
        return new StringBuilder().append("TransactionStatus{").append("code='").append(code).append('\'').append('}').toString();
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}
