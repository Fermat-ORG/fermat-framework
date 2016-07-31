package com.bitdubai.fermat_cer_api.layer.provider.utils;


import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 5/1/2016.
 */
public class CurrencyPairHelper {
    /**
     * Returns a List of CurrencyPairs that permutes a given list of Currencies
     */
    public static List<CurrencyPair> permuteCurrencyList(List<Currency> list) {
        List<CurrencyPair> currencyPairList = new ArrayList<>();

        for (Currency i : list) {
            for (Currency j : list) {
                if (!i.equals(j))
                    currencyPairList.add(new CurrencyPairImpl(i, j));
            }
        }

        return currencyPairList;
    }

    /**
     * Returns a List of CurrencyPairs that permutes two Lists of Currencies listFrom and listTo
     * also allows the inclusion of Inverse permutations
     * <p/>
     * e.g.:
     * <p/>
     * If   listFrom={A, B, C} and listTo={A, 1, 2}
     * <p/>
     * This method returns:
     * currencyPairList {A1, A2, BA, B1, B2, CA, C1, C2}
     * <p/>
     * If includeInverse is true:
     * <p/>
     * currencyPairList {A1, 1A, A2, 2A, ... C2, 2C}
     */
    public static List<CurrencyPair> permuteTwoCurrencyLists(List<Currency> listFrom, List<Currency> listTo, boolean includeInverse) {
        List<CurrencyPair> currencyPairList = new ArrayList<>();

        for (Currency i : listFrom) {
            for (Currency j : listTo) {
                if (!i.equals(j)) {
                    currencyPairList.add(new CurrencyPairImpl(i, j));
                    if (includeInverse)
                        currencyPairList.add(new CurrencyPairImpl(j, i));

                }
            }
        }

        return currencyPairList;
    }

    /**
     * Returns a List of CurrencyPairs that permutes all Fiat Currencies
     */
    public static List<CurrencyPair> permuteAllFiatCurrencies() {
        List<CurrencyPair> currencyPairList = new ArrayList<>();

        for (FiatCurrency i : FiatCurrency.values()) {
            for (FiatCurrency j : FiatCurrency.values()) {
                if (!i.equals(j))
                    currencyPairList.add(new CurrencyPairImpl(i, j));
            }
        }

        return currencyPairList;
    }

    /**
     * Returns a List of CurrencyPairs that permutes all Crypto Currencies
     */
    public static List<CurrencyPair> permuteAllCryptoCurrencies() {
        List<CurrencyPair> currencyPairList = new ArrayList<>();

        for (CryptoCurrency i : CryptoCurrency.values()) {
            for (CryptoCurrency j : CryptoCurrency.values()) {
                if (!i.equals(j))
                    currencyPairList.add(new CurrencyPairImpl(i, j));
            }
        }

        return currencyPairList;
    }

    /**
     * Returns a List of CurrencyPairs that permutes all Fiat and Crypto Currencies
     */
    public static List<CurrencyPair> permuteAllCurrencies() {
        List<CurrencyPair> currencyPairList = new ArrayList<>();

        List<Currency> allCurrencies = new ArrayList<>();
        for (FiatCurrency i : FiatCurrency.values())
            allCurrencies.add(i);
        for (CryptoCurrency i : CryptoCurrency.values())
            allCurrencies.add(i);

        for (Currency i : allCurrencies) {
            for (Currency j : allCurrencies) {
                if (!i.equals(j))
                    currencyPairList.add(new CurrencyPairImpl(i, j));
            }
        }

        return currencyPairList;
    }


}
