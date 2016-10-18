package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure.ChatMiddlewareEventActions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/15.
 */
public abstract class AbstractChatMiddlewareEventHandler<T extends FermatEvent> implements FermatEventHandler<T> {

    protected final ChatMiddlewarePluginRoot   pluginRoot  ;
    protected final ChatMiddlewareEventActions eventActions;

    public AbstractChatMiddlewareEventHandler(final ChatMiddlewarePluginRoot   pluginRoot  ,
                                              final ChatMiddlewareEventActions eventActions) {

        this.pluginRoot   = pluginRoot  ;
        this.eventActions = eventActions;
    }

    @Override
    public final void handleEvent(T fermatEvent) throws FermatException {

        if (this.pluginRoot.getStatus() == ServiceStatus.STARTED) {
            this.handleCHTEvent(fermatEvent);
        } else {
            throw new TransactionServiceNotStartedException();
        }
    }

    protected abstract void handleCHTEvent(T fermatEvent) throws FermatException;
}
