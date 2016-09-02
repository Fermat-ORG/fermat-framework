package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.MessageFailEvent;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;

/**
 * Created by Vilchez on 8/25/2016.
 */
public class MessageFailEventHandler extends AbstractChatMiddlewareEventHandler<MessageFailEvent>  {

    public MessageFailEventHandler(ChatMiddlewarePluginRoot pluginRoot, ChatMiddlewareEventActions eventActions) {
        super(pluginRoot, eventActions);
    }

    @Override
    public void handleCHTEvent(MessageFailEvent fermatEvent) throws FermatException {

        this.eventActions.incomingMessageFailEventHandler(fermatEvent.getMessageId());
    }
}
