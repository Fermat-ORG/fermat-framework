package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 22/12/15.
 */
public enum DistributionStatus implements FermatEnum {

    //ENUM DECLARATION
    ASSET_ACCEPTED("DAMA"),
    ASSET_DISTRIBUTED("DAMD"),
    ASSET_REJECTED_BY_CONTRACT("DAMRBC"),
    ASSET_REJECTED_BY_HASH("DAMRBH"),
    AVAILABLE_BALANCE_CHECKED("AVABC"),
    CHECKING_AVAILABLE_BALANCE("CAVAB"),
    CHECKING_CONTRACT("CCONT"),
    CHECKING_HASH("CHASH"),
    CONNECTION_REJECTED("CONNR"),
    CONTRACT_CHECKED("CONTC"),
    CRYPTO_RECEIVED("BTCRX"),
    DELIVERED("DELD"),
    TO_DELIVER("TODE"),
    DELIVERING("DELV"),
    DELIVERING_CANCELLED("DELC"),
    HASH_CHECKED("HASHC"),
    INCOMING_ASSET("INA"),
    REQUEST_CONNECTION("RCONN"),
    SENDING_CRYPTO("TXBTC"),
    SENDING_CRYPTO_FAILED("FTXBTC");

    //VARIABLE DECLARATION

    private String code;

    //CONSTRUCTORS

    DistributionStatus(String code) {
        this.code = code;
    }

    //PUBLIC METHODS

    public static DistributionStatus getByCode(String code) throws InvalidParameterException {
        for (DistributionStatus fenum : values()) {
            if (fenum.getCode().equals(code)) return fenum;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DistributionStatus enum.");
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    @Override
    public String getCode() {
        return code;
    }
}
