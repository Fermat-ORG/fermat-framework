package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewWritingStatusUpdate;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;


/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 28/04/16.
 */
public class IncomingNewWritingStatusUpdateEventHandler extends AbstractChatMiddlewareEventHandler<IncomingNewWritingStatusUpdate> {

    public IncomingNewWritingStatusUpdateEventHandler(ChatMiddlewarePluginRoot pluginRoot, ChatMiddlewareEventActions eventActions) {
        super(pluginRoot, eventActions);
    }

    @Override
    public void handleCHTEvent(IncomingNewWritingStatusUpdate fermatEvent) throws FermatException {

        this.eventActions.incomingWritingStatusEventHandler(fermatEvent.getSenderPk());
    }
}
