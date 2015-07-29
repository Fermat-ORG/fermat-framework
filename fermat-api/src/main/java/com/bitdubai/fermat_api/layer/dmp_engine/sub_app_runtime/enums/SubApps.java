package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums;

/**
 * Created by ciencias on 2/14/15.
 */
public enum SubApps {

    CWP_WALLET_MANAGER("Manager"),
    CWP_WALLET_RUNTIME("Runtime"),
    CWP_WALLET_STORE ("WalletStore"),
    CWP_WALLET_FACTORY ("WalletFactory"),
    CWP_DEVELOPER_APP ("DeveloperSubApp"),
    CWP_WALLET_PUBLISHER("WalletPublisher"),
    CWP_SHELL("CWP_SHELL");


    private final String code;

    SubApps(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static SubApps getByCode(String code) {

        switch (code) {
            case "Manager": return SubApps.CWP_WALLET_MANAGER;
            case "Runtime": return SubApps.CWP_WALLET_RUNTIME;
            case "WalletStore": return SubApps.CWP_WALLET_STORE;
            case "WalletFactory": return SubApps.CWP_WALLET_FACTORY;
            case "DeveloperSubApp": return SubApps.CWP_DEVELOPER_APP;
            case "WalletPublisher": return SubApps.CWP_WALLET_PUBLISHER;
        }

        /**
         * Return by default.
         */
        return null;
    }
}
