package com.bitdubai.fermat_core.layer.dmp_network_service.crypto_transmission;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_ccp_api.layer.network_service.NetworkServiceSubsystem;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_ccp_api.layer.network_service.CantStartSubsystemException;

/**
 * Created by Matias Furszyfer on 08/09/15.
 */
public class CryptoTransmissionSubsystem implements NetworkServiceSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e,null,null);
        }
    }
}