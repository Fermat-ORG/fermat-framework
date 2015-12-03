package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency</code>
 * Contains the different CryptoCurrencies available on Fermat.
 * <p/>
 * Created by ciencias on 20.12.14. *
 * Modified by Manuel Perez on 03/08/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum CryptoCurrency implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITCOIN     ("BTC"),
    CHAVEZCOIN  ("CHC"),
    LITECOIN    ("LTC")

    ;

    private final String code;

    CryptoCurrency(final String code) {
        this.code = code;
    }

    public static CryptoCurrency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BTC": return CryptoCurrency.BITCOIN;
            case "CHC": return CryptoCurrency.CHAVEZCOIN;
            case "LTC": return CryptoCurrency.LITECOIN;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the CryptoCurrency enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
