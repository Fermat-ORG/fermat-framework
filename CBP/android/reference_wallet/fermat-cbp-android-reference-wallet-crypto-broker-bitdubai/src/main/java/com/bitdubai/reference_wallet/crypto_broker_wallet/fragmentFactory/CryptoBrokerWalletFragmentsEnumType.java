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
    CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS("CBPCBWCD"),
    CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS("CBPCBWCCD"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS("CBPCBWOND"),
    CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS("CBPCBWCND"),
    CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY("CBPCBWCH"),
    CBP_CRYPTO_BROKER_WALLET_EARNINGS("CBPCBWE"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS("CBPCBWS"),
    CBP_CRYPTO_BROKER_WALLET_SET_IDENTITY("CBPCBWSI"),
    CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES("CBPCBWSM"),
    CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS("CBPCBWSE"),
    CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS("CBPCBWSP"),
    CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS("CBPCBWSL"),
    CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT("CBPCBWSBA"),
    CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD("CBPCBWCNLIW"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS_MY_LOCATIONS("CBPCBWSML"),
    CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_SETTINGS("CBPCBWCNLIS"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS_BANK_ACCOUNT("CBPCBWSSBA"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS_STOCK_MERCHANDISES("CBPCBWSSM"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS_FEE_MANAGEMENT("CBPCBWSFM"),
    CBP_CRYPTO_BROKER_WALLET_OTHER_SETTINGS("CBPCBWOS"),;

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
