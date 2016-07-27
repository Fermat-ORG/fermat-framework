package com.bitdubai.fermat_cht_core.layer.actor_network_service.chat;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ActorNetworkServicePluginSubsystem extends AbstractPluginSubsystem {
    public ActorNetworkServicePluginSubsystem() {
        super(new PluginReference(Plugins.CHAT_ACTOR_NETWORK_SERVICE));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            //TODO: IMPLEMENT DEVELOP
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
