package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 11/06/15.
 *
 */
public enum CryptoStatus {

    PENDING_SUBMIT ("PSB", 0),
    ON_CRYPTO_NETWORK ("OCN", 1),
    REVERSED_ON_CRYPTO_NETWORK ("RON", 2),
    ON_BLOCKCHAIN ("OBC", 3),
    REVERSED_ON_BLOCKCHAIN ("ROB", 4),
    IRREVERSIBLE ("IRR", 5);

    private final String code;

    private final int order;

    CryptoStatus(String Code, int order) {
        this.code = Code;
        this.order = order;
    }

    public String getCode() { return this.code; }

    public int getOrder() { return this.order; }

    public static CryptoStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "PSB":
                return CryptoStatus.PENDING_SUBMIT;
            case "OCN":
                return CryptoStatus.ON_CRYPTO_NETWORK;
            case "OBC":
                return CryptoStatus.ON_BLOCKCHAIN;
            case "IRR":
                return CryptoStatus.IRREVERSIBLE;
            case "ROB":
                return CryptoStatus.REVERSED_ON_BLOCKCHAIN;
            case "RON":
                return CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;

            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CryptoStatus enum");
        }
    }
}
