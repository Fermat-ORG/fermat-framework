package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common;

import java.io.Serializable;

public class WalletContact implements Serializable {

    private static final long serialVersionUID = -8730067976543216758L;

    public String name;

    public String address;

    public WalletContact(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "WalletContact{" +
                "name='" + name + '\'' +
                ", address='" + address +
                '}';
    }
}