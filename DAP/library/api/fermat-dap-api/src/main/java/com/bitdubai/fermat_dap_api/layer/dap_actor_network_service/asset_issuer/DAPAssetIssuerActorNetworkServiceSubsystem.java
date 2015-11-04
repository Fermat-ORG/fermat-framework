package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantStartSubsystemException;

/**
 * Created by franklin on 15/10/15.
 */
public interface DAPAssetIssuerActorNetworkServiceSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
