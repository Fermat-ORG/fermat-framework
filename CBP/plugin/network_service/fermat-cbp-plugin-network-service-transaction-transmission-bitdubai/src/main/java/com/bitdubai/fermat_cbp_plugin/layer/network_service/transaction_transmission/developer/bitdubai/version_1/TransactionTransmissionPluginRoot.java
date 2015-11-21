package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class TransactionTransmissionPluginRoot implements Plugin{

    UUID pluginId;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    @Override
    public FermatManager getManager() {
        return null;
    }
}
