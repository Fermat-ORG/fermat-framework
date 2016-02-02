package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoBrokerWalletProviderSettingImpl implements CryptoBrokerWalletProviderSetting {
    private UUID id;
    private String brokerPublicKey;
    private UUID plugin;
    private String description;

    public CryptoBrokerWalletProviderSettingImpl() {
    }

    ;

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getPlugin() {
        return plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlugin(UUID plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
