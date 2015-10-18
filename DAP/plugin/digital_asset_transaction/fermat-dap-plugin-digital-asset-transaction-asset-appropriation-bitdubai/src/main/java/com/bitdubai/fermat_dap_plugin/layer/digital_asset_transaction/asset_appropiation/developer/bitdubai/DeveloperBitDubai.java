package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.AssetAppropiationPluginRoot;

public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {
    Plugin plugin;

    public DeveloperBitDubai() {
        this.plugin = new AssetAppropiationPluginRoot();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public int getAmountToPay() {
        return 0; //TODO set a real value
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return null; //TODO set a test value
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY; //TODO verificar si es esto correcto
    }
}
