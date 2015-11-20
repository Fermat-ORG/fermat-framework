package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/10/15.
 */
public enum ReceptionStatus implements FermatEnum {
    ASSET_ACCEPTED("DAMA"),
    CHECKING_CONTRACT("CCONT"),
    CHECKING_HASH("CHASH"),
    CONTRACT_CHECKED("CONTC"),
    CRYPTO_RECEIVED("BTCRX"),
    HASH_CHECKED("HASHC"),
    RECEIVING("RXG"),
    REJECTED_BY_CONTRACT("DAMRBC"),
    REJECTED_BY_HASH("RBH"),
    UNDEFINED_REJECTION("URJ"),
    RECEPTION_FINISHED("REFI");

    private String code;

    ReceptionStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static ReceptionStatus getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "ACD":
                return ReceptionStatus.ASSET_ACCEPTED;
            case "CCONT":
                return ReceptionStatus.CHECKING_CONTRACT;
            case "CHASH":
                return ReceptionStatus.CHECKING_HASH;
            case "CONTC":
                return ReceptionStatus.CONTRACT_CHECKED;
            case "BTCRX":
                return ReceptionStatus.CRYPTO_RECEIVED;
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
            case "REFI":
                return ReceptionStatus.RECEPTION_FINISHED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ReceptionStatus enum.");
        }
    }
}
