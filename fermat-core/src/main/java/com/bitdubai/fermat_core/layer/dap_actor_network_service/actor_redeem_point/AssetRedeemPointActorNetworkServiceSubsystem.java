package com.bitdubai.fermat_core.layer.dap_actor_network_service.actor_redeem_point;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.DAPAssetRedeemPointActorNetworkServiceSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by franklin on 17/10/15.
 */
public class AssetRedeemPointActorNetworkServiceSubsystem  implements DAPAssetRedeemPointActorNetworkServiceSubsystem{
    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {

        try{
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }catch(Exception exception){
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetRedeemPointActorNetworkServiceSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
