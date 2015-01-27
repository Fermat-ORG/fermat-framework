package com.bitdubai.smartwallet.platform.layer._9_network_service.user.developer.bitdubai;

import com.bitdubai.smartwallet.platform.layer._9_network_service.NetworkService;
import com.bitdubai.smartwallet.platform.layer._9_network_service.NetworkServiceDeveloper;
import com.bitdubai.smartwallet.platform.layer._9_network_service.user.developer.bitdubai.version_1.UserService;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements NetworkServiceDeveloper {

    NetworkService mNetworkService;

    @Override
    public NetworkService getNetworkService() {
        return mNetworkService;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mNetworkService = new UserService();

    }
}
