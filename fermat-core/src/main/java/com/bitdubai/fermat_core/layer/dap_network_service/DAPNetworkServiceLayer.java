/*
* @#DAPNetworkServiceLayer.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_core.layer.dap_network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_network_service.asset_transmission.AssetTransmissionNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.DAPAssetTransmissionNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantStartSubsystemException;
/**
 * The Class <code>com.bitdubai.fermat_core.layer.dap_network_service.DAPNetworkServiceLayer</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DAPNetworkServiceLayer implements PlatformLayer {

    private Plugin assetTransmissionNetworService;

    public Plugin getAssetTransmissionNetworService() {
        return assetTransmissionNetworService;
    }



    private Plugin getPlugin(DAPAssetTransmissionNetworkServiceSubsystem dapassetTransmission) throws CantStartLayerException{
        try{

            dapassetTransmission.start();

            return dapassetTransmission.getPlugin();

        }catch(CantStartSubsystemException e){

            throw new CantStartLayerException();

        }

    }



    @Override
    public void start() throws CantStartLayerException {
        assetTransmissionNetworService = getPlugin(new AssetTransmissionNetworkServiceSubsystem());
    }
}
