package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.adapters.CurrencyTypeHierarchyAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/07/16.
 */
public class CryptoBrokerIdentityExtraData implements Serializable {
    private final Currency merchandise;
    private final Currency paymentCurrency;
    private final String extraText;


    /**
     * Default constructor with parameters
     *
     * @param merchandise     the merchandise
     * @param paymentCurrency the payment currency
     * @param extraText       the extra text
     */
    public CryptoBrokerIdentityExtraData(Currency merchandise, Currency paymentCurrency, String extraText) {
        this.merchandise = merchandise;
        this.paymentCurrency = paymentCurrency;
        this.extraText = extraText;
    }

    /**
     * This method returns the broker merchandise.
     *
     * @return the merchandise
     */
    public Currency getMerchandise() {
        return merchandise;
    }

    /**
     * This method returns the broker payment currency.
     *
     * @return the payment currency
     */
    public Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    /**
     * This method returns the extra text.
     *
     * @return the extra text
     */
    public String getExtraText() {
        return extraText;
    }

    /**
     * This method returns a json String from this class.
     *
     * @return the string with the json representation
     */
    public String toJson() {
        final Gson gson = new GsonBuilder().
                registerTypeHierarchyAdapter(Currency.class, new CurrencyTypeHierarchyAdapter()).
                create();

        return gson.toJson(this);
    }

    /**
     * This method returns a CryptoBrokerIdentityExtraData from a json String or null if the jsonString is corrupted or bad formatted.
     *
     * @param jsonString the string with the json representation
     * @return the CryptoBrokerIdentityExtraData or null
     */
    public static CryptoBrokerIdentityExtraData fromJson(String jsonString) {
        final Gson gson = new GsonBuilder().
                registerTypeHierarchyAdapter(Currency.class, new CurrencyTypeHierarchyAdapter()).
                create();

        try {
            return gson.fromJson(jsonString, CryptoBrokerIdentityExtraData.class);
        } catch (Exception e) {
            return null; //I don't want to report this exception, the data could be corrupted
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoBrokerIdentityExtraData{")
                .append("merchandise=").append(merchandise)
                .append(", paymentCurrency=").append(paymentCurrency)
                .append(", extraText='").append(extraText)
                .append('\'')
                .append('}').toString();
    }
}