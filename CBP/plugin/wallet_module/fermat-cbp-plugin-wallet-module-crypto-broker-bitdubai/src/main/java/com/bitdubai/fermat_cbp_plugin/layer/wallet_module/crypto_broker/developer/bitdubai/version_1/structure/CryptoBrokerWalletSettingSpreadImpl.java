package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

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

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getBrokerPublicKey() {
        return brokerPublicKey;
    }

    @Override
    public void setBrokerPublicKey(String brokerPublicKey) {
        this.brokerPublicKey = brokerPublicKey;
    }

    @Override
    public float getSpread() {
        return spread;
    }

    @Override
    public void setSpread(float spread) {
        this.spread = spread;
    }

    @Override
    public boolean getRestockAutomatic() {
        return restockAutomatic;
    }

    @Override
    public void setRestockAutomatic(boolean restockAutomatic) {
        this.restockAutomatic = restockAutomatic;
    }
}
