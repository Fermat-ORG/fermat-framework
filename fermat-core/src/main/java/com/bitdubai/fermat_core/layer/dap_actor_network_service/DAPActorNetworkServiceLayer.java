/*
* @#DAPActorNetworServiceLayer.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_core.layer.dap_actor_network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_actor_network_service.asset_user.AssetUserActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.DAPAssetUserActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantStartSubsystemException;


/**
 * The Class <code>com.bitdubai.fermat_core.layer.dap_actor_network_service.DAPActorNetworServiceLayer</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DAPActorNetworkServiceLayer  implements PlatformLayer {

    private Plugin assetUserActorNetworService;

    public Plugin getAssetUserActorNetworService() {
        return assetUserActorNetworService;
    }


    private Plugin getPlugin(DAPAssetUserActorNetworkServiceSubsystem dAPAssetUserActorNetworkServiceSubsystem) throws CantStartLayerException{
        try{

            dAPAssetUserActorNetworkServiceSubsystem.start();

            return dAPAssetUserActorNetworkServiceSubsystem.getPlugin();

        }catch(CantStartSubsystemException e){
            throw new CantStartLayerException();

        }
    }




    @Override
    public void start() throws CantStartLayerException {
        assetUserActorNetworService = getPlugin(new AssetUserActorNetworkServiceSubsystem());
    }
}
