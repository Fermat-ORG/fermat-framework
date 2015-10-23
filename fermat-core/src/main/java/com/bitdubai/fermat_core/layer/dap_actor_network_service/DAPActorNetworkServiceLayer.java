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
import com.bitdubai.fermat_core.layer.dap_actor_network_service.actor_issuer.AssetIssuerActorNetworkServiceSubsystem;
import com.bitdubai.fermat_core.layer.dap_actor_network_service.actor_redeem_point.AssetRedeemPointActorNetworkServiceSubsystem;
import com.bitdubai.fermat_core.layer.dap_actor_network_service.asset_user.AssetUserActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.DAPAssetIssuerActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.DAPAssetUserActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.DAPAssetRedeemPointActorNetworkServiceSubsystem;


/**
 * The Class <code>com.bitdubai.fermat_core.layer.dap_actor_network_service.DAPActorNetworServiceLayer</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DAPActorNetworkServiceLayer  implements PlatformLayer {

    private Plugin        assetUserActorNetworService;
    private Plugin      assetIssuerActorNetwokService;
    private Plugin asserRedeemPointActorNetworService;

    public Plugin getAssetUserActorNetworService() {
        return assetUserActorNetworService;
    }

    public Plugin getAssetIssuerActorNetwokService(){
        return assetIssuerActorNetwokService;
    }

    public Plugin getAssetRedeemPointActorNetwokService(){
        return asserRedeemPointActorNetworService;
    }

    private Plugin getPlugin(DAPAssetUserActorNetworkServiceSubsystem dAPAssetUserActorNetworkServiceSubsystem) throws CantStartLayerException{
        try{

            dAPAssetUserActorNetworkServiceSubsystem.start();

            return dAPAssetUserActorNetworkServiceSubsystem.getPlugin();

        }catch(CantStartSubsystemException e){
            throw new CantStartLayerException();

        }
    }

    private Plugin getPlugin(DAPAssetIssuerActorNetworkServiceSubsystem dapAssetIssuerActorNetworkServiceSubsystem) throws CantStartLayerException{
        try
        {
            dapAssetIssuerActorNetworkServiceSubsystem.start();
            return dapAssetIssuerActorNetworkServiceSubsystem.getPlugin();
        }catch(com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantStartSubsystemException e){
            throw new CantStartLayerException();
        }
    }

    private Plugin getPlugin(DAPAssetRedeemPointActorNetworkServiceSubsystem dapAssetRedeemPointActorNetworkServiceSubsystem) throws CantStartLayerException{
        try
        {
            dapAssetRedeemPointActorNetworkServiceSubsystem.start();
            return dapAssetRedeemPointActorNetworkServiceSubsystem.getPlugin();
        }catch(com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantStartSubsystemException e){
            throw new CantStartLayerException();
        }
    }

    @Override
    public void start() throws CantStartLayerException {
        assetUserActorNetworService   = getPlugin(new AssetUserActorNetworkServiceSubsystem());
        assetIssuerActorNetwokService = getPlugin(new AssetIssuerActorNetworkServiceSubsystem());
        asserRedeemPointActorNetworService = getPlugin(new AssetRedeemPointActorNetworkServiceSubsystem());
    }
}
