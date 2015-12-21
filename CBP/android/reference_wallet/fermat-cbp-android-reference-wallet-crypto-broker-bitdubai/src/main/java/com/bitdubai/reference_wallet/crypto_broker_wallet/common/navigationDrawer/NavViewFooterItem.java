package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

/**
 * Created by nelson on 19/12/15.
 */
public class NavViewFooterItem {

    private String currency;
    private String value;

    public NavViewFooterItem() {
    }

    public NavViewFooterItem(String currency, String value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public String getValue() {
        return value;
    }
}
