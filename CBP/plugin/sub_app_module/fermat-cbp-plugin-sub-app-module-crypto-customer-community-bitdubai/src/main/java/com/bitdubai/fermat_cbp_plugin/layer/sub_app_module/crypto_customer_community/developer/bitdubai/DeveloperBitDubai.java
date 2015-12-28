package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.CustomerCommunitySubAppModuleCryptoPluginRoot;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {


    public DeveloperBitDubai() {
        super(new PluginDeveloperReference(Developers.BITDUBAI));
    }

    /**
     * PluginLicensor Interface implementation.
     */
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

    @Override
    public void start() throws CantStartPluginDeveloperException {
        try {

            this.registerVersion(new CustomerCommunitySubAppModuleCryptoPluginRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartPluginDeveloperException(e, "", "Error registering plugin versions for the developer.");
        }
    }
}
