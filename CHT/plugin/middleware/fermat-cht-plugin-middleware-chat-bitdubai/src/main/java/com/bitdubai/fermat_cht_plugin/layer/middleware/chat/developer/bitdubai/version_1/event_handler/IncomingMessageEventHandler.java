package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingMessageEvent;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 17/08/16.
 */
public class IncomingMessageEventHandler  extends AbstractChatMiddlewareEventHandler<IncomingMessageEvent>  {

    public IncomingMessageEventHandler(ChatMiddlewarePluginRoot pluginRoot, ChatMiddlewareEventActions eventActions) {
        super(pluginRoot, eventActions);
    }

    @Override
    public void handleCHTEvent(IncomingMessageEvent fermatEvent) throws FermatException {

        this.eventActions.incomingMessageEventHandler(fermatEvent.getMessageMetadata());
    }
}
