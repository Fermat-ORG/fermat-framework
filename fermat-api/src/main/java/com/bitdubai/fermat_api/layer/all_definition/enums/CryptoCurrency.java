package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency</code>
 * Contains the different CryptoCurrencies available on Fermat.
 * <p/>
 * Created by ciencias on 20.12.14. *
 * Modified by Manuel Perez on 03/08/2015
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum CryptoCurrency implements Currency {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITCOIN     ("BTC", "Bitcoin"),
    DOGECOIN    ("DOGE", "Dogecoin"),
    ETHEREUM    ("ETH", "Ethereum"),
    LITECOIN    ("LTC", "Litecoin")

    ;

    private final String code;
    private final String friendlyName;

    CryptoCurrency(final String code, String friendlyName) {
        this.code = code;
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static CryptoCurrency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BTC": return CryptoCurrency.BITCOIN;
            case "DOGE": return CryptoCurrency.DOGECOIN;
            case "ETH": return CryptoCurrency.ETHEREUM;
            case "LTC": return CryptoCurrency.LITECOIN;
            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the CryptoCurrency enum"
                );
        }
    }

    public static boolean codeExists(String code) {
        try {
            getByCode(code);
            return true;
        } catch(InvalidParameterException e) {
            return false;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public CurrencyTypes getType() {
        return CurrencyTypes.CRYPTO;
    }
}
