package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/11/15.
 */
public enum ContractTransactionStatus implements FermatEnum {

    ACK_OFFLINE_MERCHANDISE("AFM"),
    ACK_ONLINE_MERCHANDISE("ANM"),
    ACK_OFFLINE_PAYMENT("AFP"),
    ACK_ONLINE_PAYMENT("ANP"),
    CHECKING_HASH("CHA"),
    CLOSING_CONTRACT("CLC"),
    CONTRACT_COMPLETED("CNC"),
    CONTRACT_CONFIRMED("COC"),
    CONTRACT_OPENED("COO"),
    CREATING_CONTRACT("CRC"),
    HASH_REJECTED("HRJ"),
    PENDING_CONFIRMATION("PEC"),
    PENDING_RESPONSE("PER"),
    PENDING_SUBMIT("PES"),
    SUBMIT_OFFLINE_MERCHANDISE("SFM"),
    SUBMIT_ONLINE_MERCHANDISE("SNM"),
    SUBMIT_OFFLINE_PAYMENT("SFP"),
    SUBMIT_ONLINE_PAYMENT("SNP"),
    ;

    String code;
    ContractTransactionStatus(String code){
        this.code=code;
    }

    //PUBLIC METHODS

    public static ContractTransactionStatus getByCode(String code) throws InvalidParameterException {
        for (ContractTransactionStatus value : values()) {
            if (value.getCode().equals(code)) return value;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the OpenContractStatus enum.");
    }

    @Override
    public String toString() {
        return "OpenContractStatus{" +
                "code='" + code + '\'' +
                '}';
    }

    //GETTER AND SETTERS
    @Override
    public String getCode() {
        return code;
    }
}
