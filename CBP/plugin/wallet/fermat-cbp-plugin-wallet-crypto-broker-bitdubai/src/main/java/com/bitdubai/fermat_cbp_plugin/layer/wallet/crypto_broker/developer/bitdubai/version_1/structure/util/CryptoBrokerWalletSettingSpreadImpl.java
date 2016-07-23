package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoBrokerWalletSettingSpreadImpl implements CryptoBrokerWalletSettingSpread {

    UUID id;
    String brokerPublicKey;
    float spread;
    boolean restockAutomatic;

    public CryptoBrokerWalletSettingSpreadImpl() {
    }

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

    @Override
/**
 * {@inheritDoc}
 */
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
    public float getSpread() {
        return spread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpread(float spread) {
        this.spread = spread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRestockAutomatic() {
        return restockAutomatic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRestockAutomatic(boolean restockAutomatic) {
        this.restockAutomatic = restockAutomatic;
    }
}
