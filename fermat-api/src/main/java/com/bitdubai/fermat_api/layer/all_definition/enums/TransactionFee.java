package com.bitdubai.fermat_api.layer.all_definition.enums;

import java.io.Serializable;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/4/16.
 */
public enum TransactionFee implements Serializable {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    LOWBITCOIN("BTCLOW", "0.00001000"),
    NORMALBITCOIN("BTCNORMAL", "0.00001500"),
    FASTBITCOIN("BTCFAST", "0.00002000"),
    LOWFERMAT("FRMLOW", "0.00001000"),
    NORMALFERMAT("FRMNORMAL", "0.00001500"),
    FASTFERMAT("FRMFAST", "0.00002000"),
    LOWDOGECOIN("DGELOW", "0.00001000"),
    NORMALDOGECOIN("DGENORMAL", "0.00001500"),
    FASTDOGECOIN("DGEFAST", "0.00002000"),
    LOWETHEREUM("ETHLOW", "0.00001000"),
    NORMALDETHEREUM("ETHNORMAL", "0.00001500"),
    FASTETHEREUM("ETHFAST", "0.00002000"),
    LOWLITECOIN("LTCLOW", "0.00001000"),
    NORMALLITECOIN("LTCNORMAL", "0.00001500"),
    FASTLITECOIN("LTCFAST", "0.00002000");


    private final String code;
    private final String friendlyName;

    TransactionFee(final String code, String friendlyName) {
        this.code = code;
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static String getByCode(String code) {
        String result = "";
        for (TransactionFee vault : TransactionFee.values()) {
            if (vault.getCode().equals(code))
                result = vault.getFriendlyName();
        }
        return result;
    }

    public String getCode() {
        return this.code;
    }

}



