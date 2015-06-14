package com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.enums;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;

/**
 * Created by toshiba on 05/03/2015.
 */
public enum Repositories {
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI("fermat-wallet-resource-package-age-kids-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_AGE_TEEN_ALL_BITDUBAI ("fermat-wallet-resource-package-age-teens-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI ("fermat-wallet-resource-package-age-adults-all-bitdubai"),
    CWP_WALLET_RUNTIME_WALLET_AGE_YOUNG_ALL_BITDUBAI ("fermat-wallet-resource-package-age-young-all-bitdubai");

    private String key;

    Repositories(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }

    public String toString(){
        return key;
    }

    public static String getValueFromType(Wallets type) {
        for (Repositories repo : Repositories.values()) {
            if (repo.name().equals(type.toString())) {
                return repo.key;
            }
        }
        // throw an IllegalArgumentException or return null
        throw new IllegalArgumentException("the given number doesn't match any Status.");
    }
}
