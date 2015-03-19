package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.Agent;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;

/**
 * Created by ciencias on 3/19/15.
 */
public class IncomingCryptoCatchUpAgent  implements Agent, DealsWithPluginDatabaseSystem {


    /**
     * Agent interface implementation.
     */

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

    }
}
