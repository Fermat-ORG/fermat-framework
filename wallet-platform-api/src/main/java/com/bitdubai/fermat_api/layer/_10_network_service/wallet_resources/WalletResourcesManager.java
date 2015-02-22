package com.bitdubai.fermat_api.layer._10_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer._10_network_service.CantCheckResourcesException;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletResourcesManager {
    

    public void checkResources() throws CantCheckResourcesException;

}
