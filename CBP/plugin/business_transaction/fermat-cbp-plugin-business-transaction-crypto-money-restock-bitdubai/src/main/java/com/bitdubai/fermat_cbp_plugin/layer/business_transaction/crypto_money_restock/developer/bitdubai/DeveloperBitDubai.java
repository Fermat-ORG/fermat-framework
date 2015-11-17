package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_restock.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;

/**
 * Created by franklin on 15/11/15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {
    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        //TODO:Revisar Esta nueva forma
        return null;
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
