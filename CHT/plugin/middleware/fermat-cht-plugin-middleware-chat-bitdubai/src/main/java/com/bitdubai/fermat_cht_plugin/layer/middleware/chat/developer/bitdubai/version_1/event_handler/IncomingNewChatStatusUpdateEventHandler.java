package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChatStatusUpdateEvent;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;

/**
 * Created by José Vilchez on 09/03/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class IncomingNewChatStatusUpdateEventHandler extends AbstractChatMiddlewareEventHandler<IncomingChatStatusUpdateEvent> {

    public IncomingNewChatStatusUpdateEventHandler(ChatMiddlewarePluginRoot pluginRoot, ChatMiddlewareEventActions eventActions) {
        super(pluginRoot, eventActions);
    }

    @Override
    public void handleCHTEvent(IncomingChatStatusUpdateEvent fermatEvent) throws FermatException {

        this.eventActions.incomingMessageStatusUpdateEventHandler(fermatEvent.getMessageMetadata());
    }
}
