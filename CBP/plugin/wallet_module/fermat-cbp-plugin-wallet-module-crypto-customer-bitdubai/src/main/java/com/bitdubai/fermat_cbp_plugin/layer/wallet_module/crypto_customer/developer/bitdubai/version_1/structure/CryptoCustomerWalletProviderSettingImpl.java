package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/01/16.
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
    public void setCustomerPublicKey(String customerPublicKeyPublicKey) {
        this.customerPublicKey = customerPublicKeyPublicKey;
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
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
