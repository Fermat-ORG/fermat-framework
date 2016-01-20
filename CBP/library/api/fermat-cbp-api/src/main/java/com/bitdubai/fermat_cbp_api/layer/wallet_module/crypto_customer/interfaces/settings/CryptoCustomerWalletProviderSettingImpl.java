package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoCustomerWalletProviderSettingImpl implements CryptoCustomerWalletProviderSetting {

    UUID   id;
    String customerPublicKey;
    UUID   plugin;
    String description;


    public CryptoCustomerWalletProviderSettingImpl(){};

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    @Override
    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    @Override
    public UUID getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(UUID plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }
}
