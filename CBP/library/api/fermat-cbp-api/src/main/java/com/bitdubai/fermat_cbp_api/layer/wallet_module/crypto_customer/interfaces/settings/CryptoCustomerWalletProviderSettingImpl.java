package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoCustomerWalletProviderSettingImpl implements CryptoCustomerWalletProviderSetting {

    public CryptoCustomerWalletProviderSettingImpl(){};

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void setId(UUID id) {

    }

    @Override
    public String getBrokerPublicKey() {
        return null;
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {

    }

    @Override
    public UUID getPlugin() {
        return null;
    }

    @Override
    public void setPlugin(UUID plugin) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }
}
