package com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.wallet_platform_api.layer._5_license.PluginLicensor;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannel;
import com.bitdubai.wallet_platform_api.layer._8_communication.CommunicationChannelDeveloper;
import com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.version_1.CloudCommunicationChannelPluginRoot;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements CommunicationChannelDeveloper, PluginLicensor {

    //TODO: Deberia ser Pluin Develper
    
    CommunicationChannel mCommunicationChannel;

    @Override
    public CommunicationChannel getCommunicationChannel() {
        return mCommunicationChannel;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mCommunicationChannel = new CloudCommunicationChannelPluginRoot();

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
