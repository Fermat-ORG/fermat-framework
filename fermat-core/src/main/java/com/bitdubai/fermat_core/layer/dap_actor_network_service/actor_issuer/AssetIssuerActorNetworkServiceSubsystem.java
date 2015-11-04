package com.bitdubai.fermat_core.layer.dap_actor_network_service.actor_issuer;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.DAPAssetIssuerActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by franklin on 17/10/15.
 */
public class AssetIssuerActorNetworkServiceSubsystem implements DAPAssetIssuerActorNetworkServiceSubsystem{
    Plugin plugin;

    @Override
    public void start() throws com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantStartSubsystemException {
        try{
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }catch(Exception exception){
            throw new com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantStartSubsystemException(FermatException.wrapException(exception),"AssetIssuerActorNetworkServiceSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
