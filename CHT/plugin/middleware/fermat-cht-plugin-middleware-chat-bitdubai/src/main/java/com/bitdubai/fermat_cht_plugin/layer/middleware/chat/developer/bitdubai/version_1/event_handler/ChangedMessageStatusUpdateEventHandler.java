package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.ChangedMessageStatusUpdateEvent;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;

/**
 * Created by José Vilchez on 09/03/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChangedMessageStatusUpdateEventHandler extends AbstractChatMiddlewareEventHandler<ChangedMessageStatusUpdateEvent> {

    public ChangedMessageStatusUpdateEventHandler(ChatMiddlewarePluginRoot pluginRoot, ChatMiddlewareEventActions eventActions) {
        super(pluginRoot, eventActions);
    }

    @Override
    public void handleCHTEvent(ChangedMessageStatusUpdateEvent fermatEvent) throws FermatException {

        this.eventActions.incomingMessageStatusUpdateEventHandler(fermatEvent.getMessageMetadata());
    }
}
