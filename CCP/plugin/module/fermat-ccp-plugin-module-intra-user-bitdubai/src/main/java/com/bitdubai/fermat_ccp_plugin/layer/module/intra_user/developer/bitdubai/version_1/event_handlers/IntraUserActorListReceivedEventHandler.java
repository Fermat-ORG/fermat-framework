package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkListReceiveEvent;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleManagerImpl;

/**
 * Created by natalia on 29/08/16.
 */
public class IntraUserActorListReceivedEventHandler implements FermatEventHandler<ActorNetworkListReceiveEvent> {

    private final IntraWalletUserModulePluginRoot pluginRoot;
    private final IntraUserModuleManagerImpl manager;

    public IntraUserActorListReceivedEventHandler(final IntraWalletUserModulePluginRoot pluginRoot, IntraUserModuleManagerImpl manager) {

        this.pluginRoot = pluginRoot;
        this.manager = manager;
    }

    @Override
    public void handleEvent(ActorNetworkListReceiveEvent fermatEvent) throws FermatException {

        System.out.println("INTRA USER ACTOR LIST FLOW -> HANDLING EVENT -> -> init");

        if (this.pluginRoot.getStatus() == ServiceStatus.STARTED)
            manager.handleActorListReceivedEvent(fermatEvent.getActorProfileList());
        else
            throw new FermatException("", null, "Plugin is not started.", "");
    }


}
