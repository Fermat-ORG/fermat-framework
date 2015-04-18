package com.bitdubai.fermat_api.layer._16_module;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Modules {
    WALLET_MANAGER ("Wallet Manager"),
    WALLET_RUNTIME ("Wallet Runtime"),
    WALLET_STORE ("Wallet Store"),
    WALLET_FACTORY ("Wallet Factory"),
    WALLET_PUBLISHER ("Wallet Publisher");

    private final String moduleName;

    Modules(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName()   { return this.moduleName; }
}
