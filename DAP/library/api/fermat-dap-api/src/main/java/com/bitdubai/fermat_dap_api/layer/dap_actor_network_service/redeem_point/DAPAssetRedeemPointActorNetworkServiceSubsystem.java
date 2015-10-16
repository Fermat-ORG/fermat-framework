package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantStartSubsystemException;

/**
 * Created by franklin on 15/10/15.
 */
public interface DAPAssetRedeemPointActorNetworkServiceSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
