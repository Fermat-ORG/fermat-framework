package com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.AssetDirectSellBusinessTransactionPluginRoot;

/**
 * Luis Torres (lutor1106@gmail.com") 16/03/16
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public DeveloperBitDubai() {
        super(new PluginDeveloperReference(Developers.BITDUBAI));
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartPluginDeveloperException {
        try {

            this.registerVersion(new AssetDirectSellBusinessTransactionPluginRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartPluginDeveloperException(e, "", "Error registering plugin versions for the developer.");
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS


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
        return "19qRypu7wrndwW4FRCxU1JPr5hvMmcQ3eh";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
    //INNER CLASSES
}