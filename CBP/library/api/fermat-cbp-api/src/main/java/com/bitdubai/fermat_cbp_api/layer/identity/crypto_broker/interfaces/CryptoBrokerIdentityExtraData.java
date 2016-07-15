package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.google.gson.Gson;

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
     * @param merchandise
     * @param paymentCurrency
     * @param extraText
     */
    public CryptoBrokerIdentityExtraData(
            Currency merchandise,
            Currency paymentCurrency,
            String extraText) {
        this.merchandise = merchandise;
        this.paymentCurrency = paymentCurrency;
        this.extraText = extraText;
    }

    /**
     * This method returns the broker merchandise.
     * @return
     */
    public Currency getMerchandise() {
        return merchandise;
    }

    /**
     * This method returns the broker payment currency.
     * @return
     */
    public Currency getPaymentCurrency() {
        return paymentCurrency;
    }

    /**
     * This method returns the extra text.
     * @return
     */
    public String getExtraText() {
        return extraText;
    }

    /**
     * This method returns a json String from this class.
     * @return
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * This method returns a CryptoBrokerIdentityExtraData from a json String.
     * @param jsonString
     * @return
     */
    public static CryptoBrokerIdentityExtraData fromJson(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, CryptoBrokerIdentityExtraData.class);
    }

    @Override
    public String toString() {
        return "CryptoBrokerIdentityExtraData{" +
                "merchandise=" + merchandise +
                ", paymentCurrency=" + paymentCurrency +
                ", extraText='" + extraText + '\'' +
                '}';
    }
}
