package com.bitdubai.fermat_p2p_core.layer.communication.network.node;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.DeveloperBitDubai;

/**
 * The Class <code>com.bitdubai.fermat_p2p_core.layer.communication.network.node.NetworkNodePluginSubsystem</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkNodePluginSubsystem extends AbstractPluginSubsystem {

    public NetworkNodePluginSubsystem() {
        super(new PluginReference(Plugins.NETWORK_NODE));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
