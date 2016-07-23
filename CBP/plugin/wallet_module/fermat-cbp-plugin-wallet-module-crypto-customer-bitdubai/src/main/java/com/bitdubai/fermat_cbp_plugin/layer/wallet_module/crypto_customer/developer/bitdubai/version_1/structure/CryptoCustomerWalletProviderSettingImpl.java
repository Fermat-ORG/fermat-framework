package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;

import java.util.UUID;

/**
 * Created by franklin on 03/01/16.
 * Updated by Nelson Ramirez on 09/02/16.
 */
public class CryptoCustomerWalletProviderSettingImpl implements CryptoCustomerWalletProviderSetting {

    UUID id;
    String customerPublicKey;
    UUID plugin;
    String description;
    private Currency currencyFrom;
    private Currency currencyTo;

    public CryptoCustomerWalletProviderSettingImpl() {
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

    @Override
    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    @Override
    public Currency getCurrencyTo() {
        return currencyTo;
    }

    @Override
    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    @Override
    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }
}
