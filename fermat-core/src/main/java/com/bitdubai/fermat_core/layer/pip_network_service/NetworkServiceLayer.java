package com.bitdubai.fermat_core.layer.pip_network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.pip_network_service.subapp_resources.SubAppResourcesSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.NetworkSubsystem;


/**
 * Created by natalia on 28/07/15.
 */
public class NetworkServiceLayer implements PlatformLayer {

    private Plugin mSubAppResources;

    public Plugin getSubAppResources() {
        return mSubAppResources;
    }

    @Override
    public void start() throws CantStartLayerException {
        /**
         * Let's try to start the Wallet Resources subsystem.
         */

        NetworkSubsystem subAppResourcesSubsystem = new SubAppResourcesSubsystem();

        try {
            subAppResourcesSubsystem.start();
            mSubAppResources = (subAppResourcesSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }
    }
}
