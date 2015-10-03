package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.developer.Developer;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.CryptoCustomerIdentityPluginRoot;

/**
 * Created by jorge on 28-09-2015.
 */
public class DeveloperBitdubai implements PluginDeveloper, PluginLicensor {
    private final Plugin plugin;

    public DeveloperBitdubai(){
        plugin = new CryptoCustomerIdentityPluginRoot();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public int getAmountToPay() {
        return 0;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return null;
    }
}
