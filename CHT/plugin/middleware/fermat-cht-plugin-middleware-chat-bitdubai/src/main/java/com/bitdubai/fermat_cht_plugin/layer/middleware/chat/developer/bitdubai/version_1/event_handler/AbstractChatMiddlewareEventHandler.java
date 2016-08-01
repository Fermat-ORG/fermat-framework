package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/15.
 */
public abstract class AbstractChatMiddlewareEventHandler implements FermatEventHandler {

    public ChatMiddlewareRecorderService chatMiddlewareRecorderService;

    public void setChatMiddlewareRecorderService(ChatMiddlewareRecorderService chatMiddlewareRecorderService) throws CantSetObjectException {
        if (chatMiddlewareRecorderService == null) {
            throw new CantSetObjectException("chatMiddlewareRecorderService is null");
        }
        this.chatMiddlewareRecorderService = chatMiddlewareRecorderService;
    }
}
