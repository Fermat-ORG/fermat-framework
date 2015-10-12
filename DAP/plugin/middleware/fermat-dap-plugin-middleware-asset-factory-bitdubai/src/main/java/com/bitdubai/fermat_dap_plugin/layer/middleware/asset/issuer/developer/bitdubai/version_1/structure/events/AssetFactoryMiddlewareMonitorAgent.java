package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;

import java.util.logging.Logger;

/**
 * Created by franklin on 12/10/15.
 */
public class AssetFactoryMiddlewareMonitorAgent implements Agent {
    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Asset Factory monitor agent starting");
    }

    @Override
    public void stop() {

    }
}
