package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/09/15.
 */
public enum TransactionStatus {
    FORMING_GENESIS("FGEN"),
    GENESIS_OBTAINED("OGEN"),
    GENESIS_SETTLED("SGEN"),
    HASH_SETTLED("SHASH"),
    ISSUING("ISSUING"),
    SENDING_BITCOINS("TXBTC"),
    SENDING_BITCOINS_FAILED("FTXBTC"),
    ISSUED("ISSUED"),
    ISSUED_FAILED("FISSUED"),
    TO_DELIVER("TDEL"),
    DELIVERING("DELG"),
    DELIVERED("DELD"),
    RECEIVED("RX");

    private String code;

    TransactionStatus (String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static TransactionStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "FGEN":
                return TransactionStatus.FORMING_GENESIS;
            case "OGEN":
                return TransactionStatus.GENESIS_OBTAINED;
            case "SGEN":
                return TransactionStatus.GENESIS_SETTLED;
            case "SHASH":
                return TransactionStatus.HASH_SETTLED;
            case "ISSUING":
                return TransactionStatus.ISSUING;
            case "TXBTC":
                return TransactionStatus.SENDING_BITCOINS;
            case "FTXBTC":
                return TransactionStatus.SENDING_BITCOINS_FAILED;
            case "ISSUED":
                return TransactionStatus.ISSUED;
            case "FISSUED":
                return TransactionStatus.ISSUED_FAILED;
            case "TDEL":
                return TransactionStatus.TO_DELIVER;
            case "DELG":
                return TransactionStatus.DELIVERING;
            case "DELD":
                return TransactionStatus.DELIVERED;
            case "RX":
                return TransactionStatus.RECEIVED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TransactionStatus enum.");
        }
    }
}
