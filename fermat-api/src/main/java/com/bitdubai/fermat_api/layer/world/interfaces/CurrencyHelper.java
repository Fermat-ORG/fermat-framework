package com.bitdubai.fermat_api.layer.world.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper</code>
 * contains a few methods that helps us to manage currencies..
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/02/2016.
 */
public class CurrencyHelper {

    public static Currency getCurrency(final CurrencyTypes type,
                                       final String code) throws InvalidParameterException {

        Currency currency;

        switch (type) {

            case CRYPTO:
                currency = CryptoCurrency.getByCode(code);
                break;

            case FIAT:
                currency = FiatCurrency.getByCode(code);
                break;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Currency Type: ").append(type).append(" - Code: ").append(code).toString(),
                        "We couldn't find a currency associated with the given information."
                );
        }

        return currency;
    }

    public static Currency getCurrency(final String typeCode,
                                       final String code) throws InvalidParameterException {

        CurrencyTypes type = CurrencyTypes.getByCode(typeCode);

        Currency currency;

        switch (type) {

            case CRYPTO:
                currency = CryptoCurrency.getByCode(code);
                break;

            case FIAT:
                currency = FiatCurrency.getByCode(code);
                break;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Currency Type: ").append(type).append(" - Code: ").append(code).toString(),
                        "We couldn't find a currency associated with the given information."
                );
        }

        return currency;
    }

    public static Currency getCurrency(final String code) throws InvalidParameterException {

        if (FiatCurrency.codeExists(code)) {
            return FiatCurrency.getByCode(code);
        }

        if (CryptoCurrency.codeExists(code)) {
            return CryptoCurrency.getByCode(code);
        }

        throw new InvalidParameterException(new StringBuilder().append("Currency Code: ").append(code).toString(),
                "We couldn't find a currency associated with the given information.");
    }
}
