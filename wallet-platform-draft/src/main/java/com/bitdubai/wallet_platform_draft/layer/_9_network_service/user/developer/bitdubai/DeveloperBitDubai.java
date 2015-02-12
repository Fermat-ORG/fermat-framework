package com.bitdubai.wallet_platform_draft.layer._9_network_service.user.developer.bitdubai;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.wallet_platform_api.layer._5_license.PluginLicensor;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkServiceDeveloper;
import com.bitdubai.wallet_platform_draft.layer._9_network_service.user.developer.bitdubai.version_1.UserService;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements NetworkServiceDeveloper, PluginLicensor {

    NetworkService mNetworkService;

    @Override
    public NetworkService getNetworkService() {
        return mNetworkService;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mNetworkService = new UserService();

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
