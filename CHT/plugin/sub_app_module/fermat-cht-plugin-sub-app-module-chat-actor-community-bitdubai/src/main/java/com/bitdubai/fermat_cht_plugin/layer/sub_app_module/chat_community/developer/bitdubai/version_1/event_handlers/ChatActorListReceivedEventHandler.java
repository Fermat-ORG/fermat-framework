package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.events.ChatActorListReceivedEvent;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.ChatActorCommunitySubAppModulePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 26/08/2016.
 *
 * @author lnacosta
 */
public class ChatActorListReceivedEventHandler implements FermatEventHandler<ChatActorListReceivedEvent> {

    private final ChatActorCommunitySubAppModulePluginRoot pluginRoot;
    private final ChatActorCommunityManager manager;

    public ChatActorListReceivedEventHandler(final ChatActorCommunitySubAppModulePluginRoot pluginRoot, ChatActorCommunityManager manager) {

        this.pluginRoot = pluginRoot;
        this.manager = manager;
    }

    @Override
    public void handleEvent(ChatActorListReceivedEvent fermatEvent) throws FermatException {

        if (this.pluginRoot.getStatus() == ServiceStatus.STARTED)
            manager.handleActorListReceivedEvent(fermatEvent.getActorProfileList());
        else
            throw new FermatException("", null, "Plugin is not started.", "");
    }
}
