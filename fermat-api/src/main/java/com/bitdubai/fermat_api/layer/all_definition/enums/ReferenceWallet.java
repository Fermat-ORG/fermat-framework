package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet</code>
 * enums the platform wallet types.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public enum ReferenceWallet {

    BASIC_WALLET_BITCOIN_WALLET ("BWBW"),
    BASIC_WALLET_DISCOUNT_WALLET ("BWDW"),
    BASIC_WALLET_FIAT_WALLET ("BWFW"),
    BASIC_WALLET_LOSS_PROTECTED_WALLET ("BWLP"),
    COMPOSITE_WALLET_MULTI_ACCOUNT("CWMA");


    private String code;

    ReferenceWallet(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ReferenceWallet getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BWBW": return ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
            case "BWDW": return ReferenceWallet.BASIC_WALLET_DISCOUNT_WALLET;
            case "BWFW": return ReferenceWallet.BASIC_WALLET_FIAT_WALLET;
            case "BWLP": return ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET;
            case "CWMA": return ReferenceWallet.COMPOSITE_WALLET_MULTI_ACCOUNT;
            default:
                //Modified by Manuel Perez on 03/08/2015
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ReferenceWallet enum");

        }
    }
}
