package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/10/15.
 */
public enum ReceptionStatus {
    ASSET_ACCEPTED("DAMA"),
    CHECKING_CONTRACT("CCONT"),
    CHECKING_HASH("CHASH"),
    CONTRACT_CHECKED("CONTC"),
    HASH_CHECKED("HASHC"),
    RECEIVING("RXG"),
    REJECTED_BY_CONTRACT("DAMRBC"),
    REJECTED_BY_HASH("RBH"),
    UNDEFINED_REJECTION("URJ");

    private String code;

    ReceptionStatus(String code){
        this.code=code;
    }

    public String getCode() { return this.code ; }

    public ReceptionStatus getByCode(String code)throws InvalidParameterException{
        switch (code){
            case "ACD":
                return ReceptionStatus.ASSET_ACCEPTED;
            case "CCONT":
                return ReceptionStatus.CHECKING_CONTRACT;
            case "CHASH":
                return ReceptionStatus.CHECKING_HASH;
            case "CONTC":
                return ReceptionStatus.CONTRACT_CHECKED;
            case "HASHC":
                return ReceptionStatus.HASH_CHECKED;
            case "RXG":
                return ReceptionStatus.RECEIVING;
            case "RBC":
                return ReceptionStatus.REJECTED_BY_CONTRACT;
            case "RBH":
                return ReceptionStatus.REJECTED_BY_HASH;
            case "URJ":
                return ReceptionStatus.UNDEFINED_REJECTION;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ReceptionStatus enum.");
        }
    }
}
