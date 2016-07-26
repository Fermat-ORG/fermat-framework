package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 11/06/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public enum CryptoStatus implements FermatEnum {

    /**
     * For best understanding, keep the elements of the enum ordered by order number.
     */

    PENDING_SUBMIT("PSB", 0),
    ON_CRYPTO_NETWORK("OCN", 1),
    REVERSED_ON_CRYPTO_NETWORK("RON", 2),
    ON_BLOCKCHAIN("OBC", 3),
    REVERSED_ON_BLOCKCHAIN("ROB", 4),
    IRREVERSIBLE("IRR", 5);

    private final String code;

    private final int order;

    CryptoStatus(String Code, int order) {
        this.code = Code;
        this.order = order;
    }

    public String getCode() {
        return this.code;
    }

    public int getOrder() {
        return this.order;
    }

    public static CryptoStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "PSB":
                return PENDING_SUBMIT;
            case "OCN":
                return ON_CRYPTO_NETWORK;
            case "OBC":
                return ON_BLOCKCHAIN;
            case "IRR":
                return IRREVERSIBLE;
            case "ROB":
                return REVERSED_ON_BLOCKCHAIN;
            case "RON":
                return REVERSED_ON_CRYPTO_NETWORK;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the CryptoStatus enum"
                );
        }
    }
}
