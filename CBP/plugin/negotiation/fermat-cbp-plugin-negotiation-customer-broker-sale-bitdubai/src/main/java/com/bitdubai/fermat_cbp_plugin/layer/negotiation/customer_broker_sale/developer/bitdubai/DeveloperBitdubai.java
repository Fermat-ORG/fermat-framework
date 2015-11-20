package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.CustomerBrokerSaleNegotiationPluginRoot;

/**
 * Created by jorge on 12-10-2015.
 */

public class DeveloperBitdubai implements PluginDeveloper, PluginLicensor {

    private Plugin plugin;

    public DeveloperBitdubai(){
        this.plugin = new CustomerBrokerSaleNegotiationPluginRoot();
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
