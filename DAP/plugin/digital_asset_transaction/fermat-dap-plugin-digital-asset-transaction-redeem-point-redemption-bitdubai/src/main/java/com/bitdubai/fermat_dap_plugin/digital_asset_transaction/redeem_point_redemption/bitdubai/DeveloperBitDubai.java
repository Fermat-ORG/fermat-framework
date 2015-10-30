package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.RedeemPointRedemptionPluginRoot;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 24/10/15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {


    //VARIABLE DECLARATION

    private Plugin plugin;

    //CONSTRUCTORS
    public DeveloperBitDubai() {
        plugin = new RedeemPointRedemptionPluginRoot();
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
    //INNER CLASSES
}
