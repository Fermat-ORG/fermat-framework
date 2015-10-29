package com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerWalletFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerWalletFragmentsEnumType> {

    MAIN_FRAGMENT("MF"),
    CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB("CBPCCWHAONT"),
    CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB("CBPCCWHAOCT"),
    CBP_CRYPTO_CUSTOMER_WALLET_MARKET_RATE_STATISTICS("CBPCCWMRS"),
    CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION("CBPCCWSN"),
    CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS("CBPCCWOND"),
    CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS("CBPCCWCND"),
    CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS("CBPCCWOCD"),
    CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS("CBPCCWCCD"),
    CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY("CBPCCWCH"),
    CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST("CBPCCWBL"),
    CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS("CBPCCWS");

    private String key;

    CryptoCustomerWalletFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static CryptoCustomerWalletFragmentsEnumType getValue(String name) {
        for (CryptoCustomerWalletFragmentsEnumType fragments : CryptoCustomerWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
