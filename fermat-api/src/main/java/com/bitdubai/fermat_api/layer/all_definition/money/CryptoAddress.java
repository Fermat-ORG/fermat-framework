package com.bitdubai.fermat_api.layer.all_definition.money;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by ciencias on 02.02.15.
 */
public class CryptoAddress implements Serializable {

    CryptoCurrency cryptoCurrency;
    String address;

    public CryptoAddress(String address, CryptoCurrency cryptoCurrency) {
        this.address = address;
        this.cryptoCurrency = cryptoCurrency;
    }

    public CryptoAddress() {
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public void setAddress(String address) {
        this.address = address;
        //TODO: validate the format according to each cryptocurrency.
    }

    public String getAddress() {
        return this.address;

    }

    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;

    }

    @Override
    public String toString() {
        return new StringBuilder().append(cryptoCurrency.toString()).append(":").append(address).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CryptoAddress) || obj == null)
            return false;

        /**
         * CryptoCurrency and Address must be equals to return true
         */
        if (((CryptoAddress) obj).getCryptoCurrency().equals(cryptoCurrency) && ((CryptoAddress) obj).getAddress().equals(address))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + ((cryptoCurrency == null ? 0 : cryptoCurrency.hashCode()));
        hash = prime * hash + ((StringUtils.isBlank(address) ? 0 : address.hashCode()));

        return hash;
    }
}
