package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

/**
 * Created by frank on 12/30/15.
 */
public class User {
    private String name;
    private boolean selected;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
