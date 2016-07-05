package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/4/16.
 */
public enum TransactionSpeed  implements  Serializable {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    LOWBITCOIN     ("0.00001000"),
    MEDBITCOIN     ("0.00001500"),
    HIGBITCOIN     ("0.00002000"),
    LOWFERMAT     ("0.00001000"),
    MEDFERMAT     ("0.00001000"),
    HIGFERMAT     ("0.00001000"),
    LOWDOGECOIN    ("0.00001000"),
    MEDDOGECOIN    ("0.00001000"),
    HIGDOGECOIN    ("0.00001000"),
    LOWETHEREUM    ("0.00001000"),
    MEDETHEREUM    ("0.00001000"),
    HIGETHEREUM    ("0.00001000"),
    LOWLITECOIN    ("0.00001000"),
    MEDLITECOIN    ("0.00001000"),
    HIGLITECOIN    ("0.00001000")

    ;

    private final String code;


    TransactionSpeed(final String code) {
        this.code = code;
        ;
    }


    public static TransactionSpeed getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BTC": return CryptoCurrency.BITCOIN;
            case "DOGE": return CryptoCurrency.DOGECOIN;
            case "ETH": return CryptoCurrency.ETHEREUM;
            case "LTC": return CryptoCurrency.LITECOIN;
            case "FRM": return CryptoCurrency.FERMAT;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the CryptoCurrency enum"
                );
        }
    }



}

