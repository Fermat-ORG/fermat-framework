package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.TransactionMetadataState</code>
 * represent the state of a message sent by the network service Crypto Transmission.
 */
public enum TransactionMetadataState implements FermatEnum {

    NOT_IN_NETWORK_SERVICE_YET("NINSY"),
    PROCESSING("PCS"),
    SENT("SNT"),
    SEEN_BY_DESTINATION_NETWORK_SERVICE("SBDNS"),
    SEEN_BY_DESTINATION_VAULT("SBDV"),
    CREDITED_IN_DESTINATION_WALLET("CIDW");

    private String code;

    TransactionMetadataState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public TransactionMetadataState getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "NINSY":
                return TransactionMetadataState.NOT_IN_NETWORK_SERVICE_YET;
            case "PCS":
                return TransactionMetadataState.PROCESSING;
            case "SNT":
                return TransactionMetadataState.SENT;
            case "SBDNS":
                return TransactionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE;
            case "SBDV":
                return TransactionMetadataState.SEEN_BY_DESTINATION_VAULT;
            case "CIDW":
                return TransactionMetadataState.CREDITED_IN_DESTINATION_WALLET;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("The code read was: ").append(code).toString(), "Missing branch in switch statement");
        }
    }
}
