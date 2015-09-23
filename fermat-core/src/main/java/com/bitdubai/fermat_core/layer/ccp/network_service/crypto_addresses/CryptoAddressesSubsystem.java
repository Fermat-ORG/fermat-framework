package com.bitdubai.fermat_core.layer.ccp.network_service.crypto_addresses;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_ccp_api.layer.network_service.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_api.layer.network_service.NetworkServiceSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesSubsystem implements NetworkServiceSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }

}