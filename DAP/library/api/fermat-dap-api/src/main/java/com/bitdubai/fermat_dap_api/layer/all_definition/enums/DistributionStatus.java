package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/09/15.
 */
public enum DistributionStatus {
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
    DELIVERING("DELV"),
    HASH_CHECKED("HASHC"),
    INCOMING_ASSET("INA"),
    REQUEST_CONNECTION("RCONN"),
    SENDING_CRYPTO("TXBTC"),
    SENDING_CRYPTO_FAILED("FTXBTC");

    private String code;

    DistributionStatus(String code){
        this.code=code;
    }
    public String getCode() { return this.code ; }

    public static DistributionStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "DAMA":
                return DistributionStatus.ASSET_ACCEPTED;
            case "DAMD":
                return DistributionStatus.ASSET_DISTRIBUTED;
            case "DAMRBC":
                return DistributionStatus.ASSET_REJECTED_BY_CONTRACT;
            case "DAMRBH":
                return DistributionStatus.ASSET_REJECTED_BY_HASH;
            case "AVABC":
                return DistributionStatus.AVAILABLE_BALANCE_CHECKED;
            case "CAVAB":
                return DistributionStatus.CHECKING_AVAILABLE_BALANCE;
            case "CCONT":
                return DistributionStatus.CHECKING_CONTRACT;
            case "CHASH":
                return DistributionStatus.CHECKING_HASH;
            case "CONNR":
                return DistributionStatus.CONNECTION_REJECTED;
            case "CONTC":
                return DistributionStatus.CONTRACT_CHECKED;
            case "BTCRX":
                return DistributionStatus.CRYPTO_RECEIVED;
            case "DELD":
                return DistributionStatus.DELIVERED;
            case "DELV":
                return DistributionStatus.DELIVERING;
            case "HASHC":
                return DistributionStatus.HASH_CHECKED;
            case "INA":
                return DistributionStatus.INCOMING_ASSET;
            case "RCONN":
                return DistributionStatus.REQUEST_CONNECTION;
            case "TXBTC":
                return DistributionStatus.SENDING_CRYPTO;
            case "FTXBTC":
                return DistributionStatus.SENDING_CRYPTO_FAILED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DistributionStatus enum.");
        }
    }
}
