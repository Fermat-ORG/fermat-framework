/*
* @#AssetTransmissionNetworkServiceSubsystem.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_core.layer.dap_network_service.asset_transmission;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.DAPAssetTransmissionNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.DeveloperBitDubai;

/**
 * The Class <code>com.bitdubai.fermat_core.layer.dap_network_service.asset_transmission.AssetTransmissionNetworkServiceSubsystem</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetTransmissionNetworkServiceSubsystem implements DAPAssetTransmissionNetworkServiceSubsystem {
    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try{
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }catch(Exception exception){
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetTransmissionrNetworkServiceSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
