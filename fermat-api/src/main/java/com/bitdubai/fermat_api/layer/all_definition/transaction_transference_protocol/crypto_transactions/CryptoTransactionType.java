package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType</code>
 * enumerates the different types that a crypto transaction can be.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public enum CryptoTransactionType implements FermatEnum {

    /**
     * For best understanding, keep the elements of the enum ordered alphabetically.
     */

    INCOMING("INC"),
    OUTGOING("OUT"),
    UNKNOWN("UKN");

    private final String code;

    CryptoTransactionType(final String Code) {
        this.code = Code;
    }

    public static CryptoTransactionType getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "INC":
                return INCOMING;
            case "OUT":
                return OUTGOING;
            case "UKN":
                return UNKNOWN;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code received is no valid for the CryptoTransactionType enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
