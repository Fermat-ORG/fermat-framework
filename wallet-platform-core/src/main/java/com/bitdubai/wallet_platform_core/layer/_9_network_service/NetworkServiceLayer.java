package com.bitdubai.wallet_platform_core.layer._9_network_service;

import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._9_network_service.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkSubsystem;
// import com.bitdubai.wallet_platform_draft.layer._9_network_service.user.UserSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class NetworkServiceLayer implements PlatformLayer {

    private NetworkService mUserService;

    public NetworkService getUserService() {
        return mUserService;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * The most essential service is the UserService. I start it now.
         */
/*
        NetworkSubsystem userSubsystem = new UserSubsystem();

        try {
            userSubsystem.start();
            mUserService = ((UserSubsystem) userSubsystem).getNetworkService();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
*/
            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
         /*
            throw new CantStartLayerException();
    
        }
   */
    }
}
