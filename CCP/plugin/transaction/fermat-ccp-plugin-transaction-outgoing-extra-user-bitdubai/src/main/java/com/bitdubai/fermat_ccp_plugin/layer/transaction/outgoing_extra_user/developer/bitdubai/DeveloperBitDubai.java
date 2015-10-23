package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;

/**
 * Created by loui on 20/02/15.
 */
public class DeveloperBitDubai extends AbstractPluginDeveloper implements PluginLicensor {

    public DeveloperBitDubai() {
        super(new OutgoingExtraUserTransactionPluginRoot());

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */


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

}