package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoBrokerWalletFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerWalletFragmentsEnumType> {

    CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB("CBPCBWHAONT"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB("CBPCBWHAOCT"),
    CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS("CBPCBWMRS"),
    CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS("CBPCBWHASS"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS("CBPCBWOND"),
    CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS("CBPCBWCND"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS("CBPCBWOCD"),
    CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS("CBPCBWCCD"),
    CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY("CBPCBWCH"),
    CBP_CRYPTO_BROKER_WALLET_CONTRACTS("CBPCBWC"),
    CBP_CRYPTO_BROKER_WALLET_EARNINGS("CBPCBWE"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS("CBPCBWS");

    private String key;

    CryptoBrokerWalletFragmentsEnumType(String key) {
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

    public static CryptoBrokerWalletFragmentsEnumType getValue(String name) {
        for (CryptoBrokerWalletFragmentsEnumType fragments : CryptoBrokerWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
